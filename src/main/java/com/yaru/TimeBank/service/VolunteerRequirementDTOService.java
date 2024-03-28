package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.RequirementDTO;

import java.util.List;

public interface VolunteerRequirementDTOService {
    Page<RequirementDTO> volunteerGetRequirementPage(int page, int pageSize,
                                                     String id,
                                                 String serviceName,
                                                 String address,
                                                 String durationHours
                                                 );


}
