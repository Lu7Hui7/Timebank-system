package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Admin;
import com.yaru.TimeBank.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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

//    /**
//     * 员工退出
//     * @param request
//     * @return
//     */
//    @PostMapping("/logout")
//    public R<String> logout(HttpServletRequest request){
//        //清理Session中保存的当前登录员工的id
//        request.getSession().removeAttribute("employee");
//        return R.success("退出成功");
//    }
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
