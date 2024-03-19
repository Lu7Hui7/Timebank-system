package com.yaru.TimeBank.controller;


import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.service.RequirementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/request")
public class RequirementController {
    private RequirementService requirementService;

    /**
     * 创建需求
     *
     * @param elderId      老人ID
     * @param requirement  需求对象
     * @return             创建需求结果
     */
    @PostMapping("/{elderId}/requirements")
    public R<String> createRequirement(
            @PathVariable Long elderId,
            @RequestBody  Requirement requirement) {

        // 从 RequirementRequest 对象中获取参数
        String serviceName = requirement.getServiceName();
        String serviceContent = requirement.getServiceContent();
        LocalDate createTime = requirement.getCreateTime();
        Long durationHours = requirement.getDurationHours();

        // 创建需求对象
        Requirement re = new Requirement();
        re.setServiceName(serviceName);
        re.setServiceContent(serviceContent);
        re.setCreateTime(createTime);
        re.setDurationHours(durationHours);

        // 设置老人ID
        re.setElderId(elderId);

        // 保存需求到数据库
        requirementService.save(re);

        return R.success("成功添加需求!");
    }



}
