package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.entity.Activity;

public interface ElderActivityDTOService{
    Page<ActivityDTO> getElderActivityPage(int page, int pageSize,
                                           String id,
                                           String serviceName,
                                           String volunteerAddress,
                                           String volunteerHours
                                                 );
}
