package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.RequirementDTO;

public interface AdminReviewedRequirementService {
    Page<RequirementDTO> getReviewedRequirementPage(int page, int pageSize,
                                                 String serviceName,
                                                 String address,
                                                 String durationHours,
                                                 String id);
}
