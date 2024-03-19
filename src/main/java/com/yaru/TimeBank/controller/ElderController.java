package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Elder;
import com.yaru.TimeBank.entity.Volunteer;
import com.yaru.TimeBank.service.ElderService;
import com.yaru.TimeBank.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
    /**
     * 根据id修改老年需求者的用户信息
     * @param id 老年需求者ID
     * @param updatedUserInfo 更新后的老年需求者用户信息
     * @return 返回操作结果
     */
    @PutMapping("/update")
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


}
