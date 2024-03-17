package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.mapper.RequirementMapper;
import com.yaru.TimeBank.service.RequirementService;
import org.springframework.stereotype.Service;

@Service
public class RequirementServiceImpl extends ServiceImpl<RequirementMapper, Requirement> implements RequirementService {
}
