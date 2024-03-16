package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Elder;
import com.yaru.TimeBank.entity.Volunteer;
import com.yaru.TimeBank.service.ElderService;
import com.yaru.TimeBank.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/elder")
public class ElderController {

    @Autowired
    private ElderService elderService;
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
            request.getSession().setAttribute("volunteers",e.getId());
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
        request.getSession().removeAttribute("elder");
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


}
