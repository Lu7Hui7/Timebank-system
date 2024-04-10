package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.entity.Elder;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.service.ElderActivityDTOService;
import com.yaru.TimeBank.service.ElderRequirementDTOService;
import com.yaru.TimeBank.service.ElderService;
import com.yaru.TimeBank.service.RequirementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/elder")
public class ElderController {

    @Autowired
    private ElderService elderService;
    @Autowired
    private RequirementService requirementService;
    @Autowired
    private ElderRequirementDTOService elderRequirementDTOService;
    @Autowired
    private ElderActivityDTOService elderActivityDTOService;
    private HttpSession session;

    /**
     * 老年需求者登录
     * @param request
     * @param elder
     * @return R
     */
    @PostMapping("/login")
    public R<Elder> login(HttpServletRequest request, @RequestBody Elder elder){

        //1、将页面提交的密码password进行md5加密处理
        String password = elder.getPassword();
        /*password = DigestUtils.md5DigestAsHex(password.getBytes());*/

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Elder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Elder::getUsername, elder.getUsername());
        Elder e = elderService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(e == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!e.getPassword().equals(password)){
            return R.error("登录失败");
        }
        //4、密码比对，如果一致
        if(e.getPassword().equals(password)){
            //6、登录成功，将员工id存入Session并返回登录成功结果
            // 获取当前请求的HttpSession对象
            session = request.getSession();

            session.setAttribute("elder",e.getId());
            // 设置session的最大不活动间隔为30分钟（30 * 60 秒）
            session.setMaxInactiveInterval(30 * 60);
        }
        return R.success(e);
    }
    /**
     * 老年需求者退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        session.removeAttribute("elder");
        return R.success("退出成功");
    }

    /**
     * 老年需求者注册
     * @param elder
     * @return
     */
    @PostMapping("/register")
    public R<String> save(HttpServletRequest request,@RequestBody Elder elder){
        log.info("老年需求者注册，老年需求者信息：{}",elder.toString());

        elder.setAccountStatus("冻结");
        elderService.save(elder);

        return R.success("老年需求者注册成功");
    }

    /**
     * 根据id修改老年需求者的用户信息
     * @param updatedUserInfo 更新后的老年需求者用户信息
     * @return 返回操作结果
     */
    @Transactional
    @PutMapping("/update")
    public R<String> updateElderUserInfo(@RequestBody(required = false) Elder updatedUserInfo) {
        if (updatedUserInfo == null) {
            return R.error("请求体为空");
        }
        Long id = (Long) session.getAttribute("elder");

        // 根据ID查询老年需求者信息
        Elder elder = elderService.getById(id);
        if (elder == null) {
            // 如果找不到对应ID的老年需求者，返回错误信息
            return R.error("找不到对应ID的老年需求者");
        }

        // 更新存在的属性到elder对象中
        if (!updatedUserInfo.getName().isEmpty()) {
            elder.setName(updatedUserInfo.getName());
        }
        if (!updatedUserInfo.getPhone().isEmpty()) {
            elder.setPhone(updatedUserInfo.getPhone());
        }
        if (!updatedUserInfo.getGender().isEmpty()) {
            elder.setGender(updatedUserInfo.getGender());
        }
        if (!updatedUserInfo.getAddress().isEmpty()) {
            elder.setAddress(updatedUserInfo.getAddress());
        }
        if (!updatedUserInfo.getIdentityNumber().isEmpty()) {
            elder.setIdentityNumber(updatedUserInfo.getIdentityNumber());
        }
        if (!updatedUserInfo.getAccountStatus().isEmpty()) {
            elder.setAccountStatus(updatedUserInfo.getAccountStatus());
        }
        if (!updatedUserInfo.getPhysical().isEmpty()) {
            elder.setPhysical(updatedUserInfo.getPhysical());
        }
        if (!updatedUserInfo.getRemark().isEmpty()) {
            elder.setRemark(updatedUserInfo.getRemark());
        }
        if (!updatedUserInfo.getChildrenPhone().isEmpty()) {
            elder.setChildrenPhone(updatedUserInfo.getChildrenPhone());
        }

        // 更新老年需求者信息
        elderService.updateById(elder);

        return R.success("老年需求者用户信息已更新");
    }


    /**
     * 创建需求
     *
     * @param requirement  需求对象
     * @return             创建需求结果
     */
    @PostMapping("/request/upload")
    public R<String> createRequirement(
            @RequestBody Requirement requirement) {
        log.info("elderId={} requirement = {}",  requirement.toString());

        Long elderId = (Long)session.getAttribute("elder");
        requirement.setCreateTime(LocalDateTime.now());
        requirement.setLastTime(LocalDateTime.now());

        requirement.setStatus("待审核");

        // 设置老人ID
        requirement.setElderId(elderId);
        requirement.setId(0);
        // 保存需求到数据库
        requirementService.save(requirement);

        return R.success("成功添加需求!");
    }
    /**
     * 分页展示老人历史需求
     *
     * @param page        当前页码
     * @param pageSize    每页大小
     * @param serviceName 服务名称
     * @param address     地址
     * @param durationHours 服务时长
     * @param id          ID
     * @return 返回分页查询结果
     */
    @GetMapping("/request/page")
    public R<Page<RequirementDTO>> elderRequestPage(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String durationHours,
            @RequestParam(required = false) String id) {
        log.info("Page = {}, PageSize = {},elderId = {}, ServiceName = {}, Address = {}, DurationHours = {}, Id = {}",
                page, pageSize, serviceName, address, durationHours, id);
        Long elderId = (Long)session.getAttribute("elder");
        // 调用 Service 层方法执行分页查询
        Page<RequirementDTO> resultPage = elderRequirementDTOService.getElderRequirementPage(page, pageSize,
                elderId.intValue(),
                serviceName, address,
                durationHours, id);

        // 返回分页查询结果
        return R.success(resultPage);
    }

        /**
         * 根据请求体中的参数，修改老人需求表的信息
         * @param id 老人需求表的ID
         * @RequestBody updatedRequirement 修改后的老人需求表信息
         * @return 返回操作结果
         */
        @Transactional
        @PutMapping("/request/update")
        public R<String> updateRequirement(@RequestParam("id") Integer id, @RequestBody(required = false) Requirement updatedRequirement) {
            // 根据ID查询老人需求表信息
            Requirement requirement = requirementService.getById(id);
            if (requirement == null) {
                // 如果找不到对应ID的老人需求表，返回错误信息
                return R.error("找不到对应ID的老人需求表");
            }
            if(requirement.getStatus().equals("审核通过") || requirement.getStatus().equals("待审核")){
                // 更新审核状态为“审核通过”
                requirement.setServiceName(updatedRequirement.getServiceName());
                requirement.setServiceContent(updatedRequirement.getServiceContent());
                requirement.setDurationHours(updatedRequirement.getDurationHours());
                requirement.setLastTime(LocalDateTime.now());

                // 更新老人需求表信息
                requirementService.updateById(requirement);

                return R.success("老人需求表信息已更新");
            }
            else{
                return R.error("需求表状态不符合更改权限，无法进行更改操作");
            }

        }
    /**
     * 根据ID删除老人需求表信息
     * @param id 老人需求表的ID
     * @return 返回操作结果
     */
    @Transactional
    @DeleteMapping("/request/delete")
    public R<String> deleteRequirementById(@RequestParam("id") Integer id) {
        // 检查是否存在对应ID的老人需求表信息
        Requirement existingRequirement = requirementService.getById(id);
        if (existingRequirement == null) {
            // 如果找不到对应ID的老人需求表，返回错误信息
            return R.error("找不到对应ID的老人需求表");
        }
        if(existingRequirement.getStatus().equals("审核通过") || existingRequirement.getStatus().equals("待审核")){
            // 根据ID删除老人需求表信息
            boolean deleted = requirementService.removeById(id);
            if (!deleted) {
                // 如果删除失败，返回错误信息
                return R.error("删除老人需求表信息时出错");
            }
            else {
                return R.success("成功删除老人需求表信息");
            }
        }
        else{
            return R.error("需求表状态不符合删除操作权限，无法进行删除");
        }
    }
    /**
     * 分页展示志愿者活动
     *
     * @param page        当前页码
     * @param pageSize    每页大小
     * @param id          ID
     * @param activityName 服务名称
     * @param volunteerAddress     地址
     * @param volunteerHours 服务时长
     * @return 返回分页查询结果
     */
    @GetMapping("/activity/page")
    public R<Page<ActivityDTO>> activityPage(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String activityName,
            @RequestParam(required = false) String volunteerAddress,
            @RequestParam(required = false) String volunteerHours) {
        log.info("Page = {}, PageSize = {}, id = {}, activityName = {}, volunteerAddress = {}, volunteerHours = {}",
                page, pageSize, id, activityName, volunteerAddress, volunteerHours);

        // 调用 Service 层方法执行分页查询
        Page<ActivityDTO> resultPage = elderActivityDTOService.getElderActivityPage(page, pageSize, id,
                activityName,
                volunteerAddress, volunteerHours);

        // 返回分页查询结果
        return R.success(resultPage);
    }

}
