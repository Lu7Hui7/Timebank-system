package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.entity.Activity;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.entity.Volunteer;
import com.yaru.TimeBank.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private VolunteerActivityDTOService volunteerActivityDTOService;
    @Autowired
    private VolunteerRequirementDTOService volunteerRequirementDTOService;
    @Autowired
    private RequirementService requirementService;
    private HttpSession session;

    /**
     * 志愿者登录
     * @param request
     * @param volunteer
     * @return R
     */
    @PostMapping("/login")
    public R<Volunteer> login(HttpServletRequest request, @RequestBody Volunteer volunteer){

        //1、将页面提交的密码password进行md5加密处理
        String password = volunteer.getPassword();
        /*password = DigestUtils.md5DigestAsHex(password.getBytes());*/

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Volunteer::getUsername, volunteer.getUsername());
        Volunteer v = volunteerService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(v == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!v.getPassword().equals(password)){
            return R.error("登录失败");
        }
        //4、密码比对，如果一致
        if(v.getPassword().equals(password)){

            // 获取当前请求的HttpSession对象
            session = request.getSession();

            session.setAttribute("volunteer",v.getId());
            // 设置session的最大不活动间隔为30分钟（30 * 60 秒）
            session.setMaxInactiveInterval(30 * 60);
        }
        return R.success(v);
    }
    /**
     * 志愿者退出
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(){
        //清理Session中保存的当前登录员工的id
        session.removeAttribute("volunteer");
        return R.success("退出成功");
    }

    /**
     * 志愿者注册
     * @param volunteer
     * @return
     */
    @PostMapping("/register")
    public R<String> save(HttpServletRequest request,@RequestBody Volunteer volunteer){
        log.info("志愿者注册，志愿者信息：{}",volunteer.toString());

        volunteer.setAccountStatus("冻结");
        volunteerService.save(volunteer);

        return R.success("志愿者注册成功");
    }
    /**
     * 根据id修改志愿者者的用户信息
     * @param updatedUserInfo 更新后的志愿者者用户信息
     * @return 返回操作结果
     */
    @PutMapping("/update")
    public R<String> updateVolunteerUserInfo( @RequestBody(required = false) Volunteer updatedUserInfo) {
        if (updatedUserInfo == null) {
            return R.error("请求体为空");
        }
        // 从Session中获取志愿者ID
        Long volunteerId = (Long) session.getAttribute("volunteer");
        if (volunteerId == null) {
            return R.error("未登录或登录已失效，请重新登录！");
        }

        // 根据ID查询老年需求者信息
        Volunteer volunteer = volunteerService.getById(volunteerId);
        if (volunteer == null) {
            // 如果找不到对应ID的老年需求者，返回错误信息
            return R.error("找不到对应ID的志愿者");

        }

        // 使用BeanUtils.copyProperties()方法将请求体中的属性复制到老年需求者对象中
        BeanUtils.copyProperties(updatedUserInfo, volunteer);

        // 更新老年需求者信息
        volunteerService.updateById(volunteer);

        return R.success("志愿者信息已更新");
    }

    /**
     * 创建需求
     *
     * @param activity     活动对象
     * @return 创建需求结果
     */
    @PostMapping("/activity/upload")
    public R<String> createActivity(
            @RequestBody Activity activity) {
        // 从Session中获取志愿者ID
        Long volunteerId = (Long) session.getAttribute("volunteer");
        if (volunteerId == null) {
            return R.error("未登录或登录已失效，请重新登录！");
        }

        try {
            log.info("volunteerId={} requirement = {}", volunteerId, activity.toString());

            activity.setActivityStatus("待审核");

            // 设置志愿者ID
            activity.setVolunteerId(volunteerId);

            // 保存需求到数据库
            activityService.save(activity);

            return R.success("志愿者成功发布活动!");
        } catch (Exception e) {
            log.error("活动创建失败：{}", e.getMessage());
            return R.error("活动创建失败，请稍后重试！");
        }
    }

    /**
     * 分页展示志愿者历史活动
     *
     * @param page        当前页码
     * @param pageSize    每页大小
     * @param activityName 服务名称
     * @param address     地址
     * @param volunteerHours 服务时长
     * @param id          ID
     * @return 返回分页查询结果
     */
    @GetMapping("/activity/page")
    public R<Page<ActivityDTO>> volunteerActivityPage(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) String activityName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String volunteerHours,
            @RequestParam(required = false) String id) {
        log.info("Page = {}, PageSize = {},  activityName = {}, address = {}, volunteerHours = {}, id = {}",
                page, pageSize,  activityName, address, volunteerHours, id);
        Long volunteerId = (Long) session.getAttribute("volunteer");
        if (volunteerId == null) {
            return R.error("未登录或登录已失效，请重新登录！");
        }
        // 调用 Service 层方法执行分页查询
        Page<ActivityDTO> resultPage = volunteerActivityDTOService.getVolunteerActivityPage(page, pageSize,
                volunteerId, activityName, address, volunteerHours, id);

        // 返回分页查询结果
        return R.success(resultPage);
    }
    /**
     * 根据请求体中的参数，修改志愿者活动表信息
     * @param id 志愿者活动的ID
     * @RequestBody updatedActivity 修改后的志愿者活动表信息
     * @return 返回操作结果
     */
    @Transactional
    @PutMapping("/activity/update")
    public R<String> updateActivity(@RequestParam Integer id, @RequestBody(required = false) Activity updatedActivity) {
        // 根据ID查询老人需求表信息
        Activity activity = activityService.getById(id);

        if (activity == null) {
            // 如果找不到对应ID的老人需求表，返回错误信息
            return R.error("找不到对应ID的志愿者活动表");
        }
        if(activity.getActivityStatus().equals("审核通过") || activity.getActivityStatus().equals("待审核")){
            // 更新审核状态为“审核通过”
            activity.setActivityName(updatedActivity.getActivityName());
            activity.setActivityContent(updatedActivity.getActivityContent());
            activity.setVolunteerHours(updatedActivity.getVolunteerHours());

            // 更新老人需求表信息
            activityService.updateById(activity);

            return R.success("志愿者活动表已更新");
        }
        else{
            return R.error("志愿者活动表状态不符合更改权限，无法进行更改操作");
        }

    }
    /**
     * 根据ID删除志愿者活动
     * @param id 志愿者活动的ID
     * @return 返回操作结果
     */
    @Transactional
    @DeleteMapping("/activity/delete")
    public R<String> deleteActivityById(@RequestParam Integer id) {
        // 检查是否存在对应ID的老人需求表信息
        Activity existingActivity = activityService.getById(id);
        if (existingActivity == null) {
            // 如果找不到对应ID的老人需求表，返回错误信息
            return R.error("找不到对应ID的志愿者申请表");
        }
        boolean deleted = false;
        if(existingActivity.getActivityStatus().equals("审核通过") || existingActivity.getActivityStatus().equals("待审核")){
            // 根据ID删除老人需求表信息
            deleted = activityService.removeById(id);
            if (!deleted) {
                // 如果删除失败，返回错误信息
                return R.error("删除志愿者活动表时出错");
            }
            return R.success("成功删除志愿者活动表信息");
        }
        else{
            return R.error("该活动状态不符合删除操作权限");
        }
    }

    /**
     * 分页展示老人历史需求
     *
     * @param page          当前页码
     * @param pageSize      每页大小
     * @param serviceName   服务名称
     * @param address       地址
     * @param durationHours 服务时长
     * @param id            ID
     * @return 返回分页查询结果
     */
    @GetMapping("/request/page")
    public R<Page<RequirementDTO>> elderAppRequestPage(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String durationHours,
            @RequestParam(required = false) String id) {

        log.info("Page = {}, PageSize = {} Id = {} ServiceName = {}, Address = {}, DurationHours = {}, ",
                page, pageSize, id, serviceName, address, durationHours);

        // 调用 Service 层方法执行分页查询
        Page<RequirementDTO> resultPage = volunteerRequirementDTOService.volunteerGetRequirementPage(page, pageSize,
                id, serviceName, address, durationHours);

        // 返回分页查询结果
        return R.success(resultPage);
    }

    /**
     * 响应老人需求
     * @param requirementId 老人需求表的ID
     * @return 返回操作结果
     */
    @Transactional
    @PutMapping("/response/request")
    public R<String> responseRequirement(@RequestParam("requirementId") int requirementId) {
        // 根据ID查询老人需求表信息
        Requirement requirement = requirementService.getById(requirementId);
        if (requirement == null) {
            // 如果找不到对应ID的老人需求表，返回错误信息
            return R.error("找不到对应ID的老人需求表");
        }
        Long vid = (Long)session.getAttribute("volunteer");
        // 将活动ID设置到老人需求表对象中
        requirement.setVolunteerId(vid);
        requirement.setStatus("进行中");

        // 更新老人需求表信息
        requirementService.updateById(requirement);

        return R.success("响应需求成功");
    }
}
