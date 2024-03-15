package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Volunteer;
import com.yaru.TimeBank.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;
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
            //6、登录成功，将员工id存入Session并返回登录成功结果
            request.getSession().setAttribute("volunteers",v.getId());
        }
        return R.success(v);
    }
}
