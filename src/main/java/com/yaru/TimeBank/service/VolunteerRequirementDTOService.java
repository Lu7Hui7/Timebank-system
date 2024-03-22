package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.RequirementDTO;

public interface VolunteerRequirementDTOService {
    Page<RequirementDTO> volunteerGetRequirementPage(int page, int pageSize,
                                                     String id,
                                                 String serviceName,
                                                 String address,
                                                 String durationHours
                                                 );
}
