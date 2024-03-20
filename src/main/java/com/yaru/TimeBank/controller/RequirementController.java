package com.yaru.TimeBank.controller;


import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.service.RequirementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/request")
public class RequirementController {
    @Autowired
    private RequirementService requirementService;

/*    *//**
     * 创建需求
     *
     * @param elderId      老人ID
     * @param requirement  需求对象
     * @return             创建需求结果
     *//*
    @PostMapping("/request/upload")
    public R<String> createRequirement(
            @RequestParam Long elderId,
            @RequestBody Requirement requirement) {
        log.info("elderId={} requirement = {}", elderId, requirement.toString());


        requirement.setCreateTime(LocalDate.now());
        requirement.setLastTime(LocalDate.now());

        requirement.setStatus("待审核");

        // 设置老人ID
        requirement.setElderId(elderId);

        // 保存需求到数据库
        requirementService.save(requirement);

        return R.success("成功添加需求!");
    }*/



}
