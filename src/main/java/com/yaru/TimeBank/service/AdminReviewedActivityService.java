package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;

public interface AdminReviewedActivityService {
    Page<ActivityDTO> getReviewedActivityPage(int page, int pageSize,
                                           String id,
                                           String activityName,
                                           String address,
                                           String volunteerHours);
}
