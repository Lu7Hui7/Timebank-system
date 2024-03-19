package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Elder;
import com.yaru.TimeBank.entity.Volunteer;
import com.yaru.TimeBank.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
    /**
     * 志愿者退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("volunteer");
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
     * @param id 志愿者ID
     * @param updatedUserInfo 更新后的志愿者者用户信息
     * @return 返回操作结果
     */
    @PutMapping("/update")
    public R<String> updateVolunteerUserInfo(@RequestParam("id") Long id, @RequestBody(required = false) Volunteer updatedUserInfo) {
        if (updatedUserInfo == null) {
            return R.error("请求体为空");
        }

        // 根据ID查询老年需求者信息
        Volunteer volunteer = volunteerService.getById(id);
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

}
