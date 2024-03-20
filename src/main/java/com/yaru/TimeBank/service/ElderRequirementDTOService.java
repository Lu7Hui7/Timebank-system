package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.RequirementDTO;

public interface ElderRequirementDTOService {
    Page<RequirementDTO> getElderRequirementPage(int page, int pageSize,
                                                 int elderId,
                                                 String serviceName,
                                                 String address,
                                                 String durationHours,
                                                 String id);
}
