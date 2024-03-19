package com.yaru.TimeBank.controller;


import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.service.RequirementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/request")
public class RequirementController {
    private RequirementService requirementService;

}
