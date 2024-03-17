package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.dto.AdminActivityDTO;
import com.yaru.TimeBank.dto.AdminRequirementDTO;
import com.yaru.TimeBank.entity.*;
import com.yaru.TimeBank.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ElderService elderService;
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private AdminRequirementService adminRequirementService;
    @Autowired
    private RequirementService requirementService;
    @Autowired
    private AdminActivityService adminActivityService;
    @Autowired
    private ActivityService activityService;
    /**
     * 管理员登录
     * @param request
     * @param admin
     * @return R
     */
    @PostMapping("/login")
    public R<Admin> login(HttpServletRequest request,@RequestBody Admin admin){

        //1、将页面提交的密码password进行md5加密处理
        String password = admin.getPassword();
        /*password = DigestUtils.md5DigestAsHex(password.getBytes());*/

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,admin.getUsername());
        Admin ad = adminService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(ad == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!ad.getPassword().equals(password)){
            return R.error("登录失败");
        }
        //4、密码比对，如果一致
        if(ad.getPassword().equals(password)){
            //6、登录成功，将员工id存入Session并返回登录成功结果
            request.getSession().setAttribute("admin",ad.getId());
        }
        return R.success(ad);
    }

    /**
     * 管理员退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("admin");
        return R.success("退出成功");
    }
    /**
     * 老年需求者信息分页查询
     * @param page
     * @param pageSize
     * @param params
     * @return
     */
    @GetMapping("/elder/page")
    public R<Page<Elder>> elderPage(int page, int pageSize, @RequestParam(required = false) String[] params) {
        log.info("page = {}, pageSize = {}, params = {}", page, pageSize, Arrays.toString(params));

        // 构造分页构造器
        Page<Elder> pageInfo = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Elder> queryWrapper = new LambdaQueryWrapper<>();

        // 如果参数数组不为空，则根据参数数组中的条件添加相应的模糊查询条件
        if (params != null && params.length > 0) {
            // 遍历参数数组，解析参数并添加相应的模糊查询条件
            for (String param : params) {
                String[] keyValue = param.split("=", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    // 根据参数的键值对添加模糊查询条件
                    if ("id".equals(key)) {
                        queryWrapper.like(Elder::getId, value);
                    } else if ("name".equals(key)) {
                        queryWrapper.like(Elder::getName, value);
                    } else if ("address".equals(key)) {
                        queryWrapper.like(Elder::getAddress, value);
                    } else if ("identityNumber".equals(key)) {
                        queryWrapper.like(Elder::getIdentityNumber, value);
                    } else if ("accountStatus".equals(key)) {
                        queryWrapper.like(Elder::getAccountStatus, value);
                    }
                }
            }
        }

        // 执行查询
        elderService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }


    /**
     * 志愿者者信息分页查询
     * @param page
     * @param pageSize
     * @param params
     * @return
     */
    @GetMapping("/volunteer/page")
    public R<Page<Volunteer>> volunteerPage(int page, int pageSize,
                                            @RequestParam(required = false) String[] params) {
        log.info("page = {}, pageSize = {}, params = {}", page, pageSize, Arrays.toString(params));

        // 构造分页构造器
        Page<Volunteer> pageInfo = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();

        // 如果参数数组不为空，则遍历参数数组，解析参数并添加相应的模糊查询条件
        if (params != null && params.length > 0) {
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    // 根据参数的键值对添加模糊查询条件
                    if ("id".equals(key)) {
                        queryWrapper.like(Volunteer::getId, value);
                    } else if ("name".equals(key)) {
                        queryWrapper.like(Volunteer::getName, value);
                    } else if ("address".equals(key)) {
                        queryWrapper.like(Volunteer::getAddress, value);
                    } else if ("identityNumber".equals(key)) {
                        queryWrapper.like(Volunteer::getIdentityNumber, value);
                    } else if ("accountStatus".equals(key)) {
                        queryWrapper.like(Volunteer::getAccountStatus, value);
                    }
                }
            }
        }

        // 执行查询
        volunteerService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id将老年需求者账户状态修改为激活
     * @param id 老年需求者ID
     * @return 返回操作结果
     */
    @PutMapping("/elder/activate")
    public R<String> activateElderAccount(@RequestParam("id") Long id) {
        // 根据ID查询老年需求者信息
        Elder elder = elderService.getById(id);
        if (elder == null) {
            // 如果找不到对应ID的老年需求者，返回错误信息
            return R.error("找不到对应ID的老年需求者");
        }

        // 设置老年需求者的账户状态为激活
        elder.setAccountStatus("激活");

        // 更新老年需求者信息
        elderService.updateById(elder);

        return R.success("老年需求者账户已激活");
    }

    /**
     * 根据id将志愿者账户状态修改为激活
     * @param id 志愿者者ID
     * @return 返回操作结果
     */
    @PutMapping("/volunteer/activate")
    public R<String> activateVolunteerAccount(@RequestParam("id") Long id) {
        // 根据ID查询老年需求者信息
        Volunteer volunteer = volunteerService.getById(id);
        if (volunteer == null) {
            // 如果找不到对应ID的老年需求者，返回错误信息
            return R.error("找不到对应ID的志愿者");
        }

        // 设置老年需求者的账户状态为激活
        volunteer.setAccountStatus("激活");

        // 更新老年需求者信息
        volunteerService.updateById(volunteer);

        return R.success("志愿者者账户已激活");
    }


    /**
     * 根据id修改老年需求者的用户信息
     * @param id 老年需求者ID
     * @param updatedUserInfo 更新后的老年需求者用户信息
     * @return 返回操作结果
     */
    @PutMapping("/elder/update")
    public R<String> updateElderUserInfo(@RequestParam("id") Long id, @RequestBody(required = false) Elder updatedUserInfo) {
        if (updatedUserInfo == null) {
            return R.error("请求体为空");
        }

        // 根据ID查询老年需求者信息
        Elder elder = elderService.getById(id);
        if (elder == null) {
            // 如果找不到对应ID的老年需求者，返回错误信息
            return R.error("找不到对应ID的老年需求者");
        }

        // 使用BeanUtils.copyProperties()方法将请求体中的属性复制到老年需求者对象中
        BeanUtils.copyProperties(updatedUserInfo, elder);

        // 更新老年需求者信息
        elderService.updateById(elder);

        return R.success("老年需求者用户信息已更新");
    }

    /**
     * 根据id修改志愿者者的用户信息
     * @param id 志愿者ID
     * @param updatedUserInfo 更新后的志愿者者用户信息
     * @return 返回操作结果
     */
    @PutMapping("/volunteer/update")
    public R<String> updateVolunteerUserInfo(@RequestParam("id") Long id, @RequestBody(required = false) Volunteer updatedUserInfo) {
        if (updatedUserInfo == null) {
            return R.error("请求体为空");
        }

        // 根据ID查询老年需求者信息
        Volunteer volunteer = volunteerService.getById(id);
        if (volunteer == null) {
            // 如果找不到对应ID的老年需求者，返回错误信息
            return R.error("找不到对应ID的老年需求者");
        }

        // 使用BeanUtils.copyProperties()方法将请求体中的属性复制到老年需求者对象中
        BeanUtils.copyProperties(updatedUserInfo, volunteer);

        // 更新老年需求者信息
        volunteerService.updateById(volunteer);

        return R.success("志愿者信息已更新");
    }
    /**
     * 分页查询老年需求者需求信息
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param name 搜索关键字
     * @return 返回分页查询结果
     */
    @GetMapping("/request/page")
    public R<Page<AdminRequirementDTO>> elderRequestPage(@RequestParam int page,
                                                             @RequestParam int pageSize,
                                                             @RequestParam(required = false) String name) {
        log.info("Page = {}, PageSize = {}, Name = {}", page, pageSize, name);

        // 调用 Service 层方法执行分页查询
        Page<AdminRequirementDTO> resultPage = adminRequirementService.getAdminRequirementPage(page, pageSize, name);

        // 返回分页查询结果
        return R.success(resultPage);
    }


    /**
     * 根据请求体中的参数，修改老人需求表的审核状态
     * @param id 老人需求表的ID
     * @return 返回操作结果
     */
    @PutMapping("/review/request")
    public R<String> reviewRequirementStatus(@RequestParam("id") Long id) {
        // 根据ID查询老人需求表信息
        Requirement requirement = requirementService.getById(id);
        if (requirement == null) {
            // 如果找不到对应ID的老人需求表，返回错误信息
            return R.error("找不到对应ID的老人需求表");
        }

        // 更新审核状态
        requirement.setStatus("审核通过");

        // 更新老人需求表信息
        requirementService.updateById(requirement);

        return R.success("老人需求表的审核状态已更新");
    }

    /**
     * 分页查询志愿者活动信息
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param name 搜索关键字
     * @return 返回分页查询结果
     */
    @GetMapping("/activity/page")
    public R<Page<AdminActivityDTO>> volunteerActivityPage(@RequestParam int page,
                                                             @RequestParam int pageSize,
                                                             @RequestParam(required = false) String name) {
        log.info("Page = {}, PageSize = {}, Name = {}", page, pageSize, name);

        // 调用 Service 层方法执行分页查询
        Page<AdminActivityDTO> resultPage = adminActivityService.getAdminActivityPage(page, pageSize, name);

        // 返回分页查询结果
        return R.success(resultPage);
    }

    /**
     * 根据请求体中的参数，修改活动表的审核状态
     * @param id 活动表的ID
     * @return 返回操作结果
     */
    @PutMapping("/review/activity")
    public R<String> reviewActivityStatus(@RequestParam("id") Long id) {
        // 根据ID查询活动表信息
        Activity activity = activityService.getById(id);
        if (activity == null) {
            // 如果找不到对应ID的活动表，返回错误信息
            return R.error("找不到对应ID的活动表");
        }

        // 更新审核状态
        activity.setActivityStatus("审核通过");

        // 更新活动表信息
        activityService.updateById(activity);

        return R.success("活动表的审核状态已更新");
    }

//
//    /**
//     * 新增员工
//     * @param admin
//     * @return
//     */
//    @PostMapping
//    public R<String> save(HttpServletRequest request,@RequestBody Admin admin){
//        log.info("新增员工，员工信息：{}",admin.toString());
//
//        //设置初始密码123456，需要进行md5加密处理
//        admin.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//
//        //employee.setCreateTime(LocalDateTime.now());
//        //employee.setUpdateTime(LocalDateTime.now());
//
//        //获得当前登录用户的id
//        //Long empId = (Long) request.getSession().getAttribute("employee");
//
//        //employee.setCreateUser(empId);
//        //employee.setUpdateUser(empId);
//
//        adminService.save(admin);
//
//        return R.success("新增员工成功");
//    }
//
//    /**
//     * 员工信息分页查询
//     * @param page
//     * @param pageSize
//     * @param name
//     * @return
//     */
//    @GetMapping("/page")
//    public R<Page> page(int page,int pageSize,String name){
//        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);
//
//        //构造分页构造器
//        Page pageInfo = new Page(page,pageSize);
//
//        //构造条件构造器
//        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper();
//        //添加过滤条件
//        queryWrapper.like(StringUtils.isNotEmpty(name),Admin::getName,name);
//        //添加排序条件
//        queryWrapper.orderByDesc(Admin::getUpdateTime);
//
//        //执行查询
//        adminService.page(pageInfo,queryWrapper);
//
//        return R.success(pageInfo);
//    }
//
//    /**
//     * 根据id修改员工信息
//     * @param admin
//     * @return
//     */
//    @PutMapping
//    public R<String> update(HttpServletRequest request,@RequestBody Admin admin){
//        log.info(admin.toString());
//
//        long id = Thread.currentThread().getId();
//        log.info("线程id为：{}",id);
//        //Long empId = (Long)request.getSession().getAttribute("employee");
//        //employee.setUpdateTime(LocalDateTime.now());
//        //employee.setUpdateUser(empId);
//        adminService.updateById(admin);
//
//        return R.success("员工信息修改成功");
//    }
//
//    /**
//     * 根据id查询员工信息
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    public R<Admin> getById(@PathVariable Long id){
//        log.info("根据id查询员工信息...");
//        Admin employee = adminService.getById(id);
//        if(employee != null){
//            return R.success(employee);
//        }
//        return R.error("没有查询到对应员工信息");
//    }
}
