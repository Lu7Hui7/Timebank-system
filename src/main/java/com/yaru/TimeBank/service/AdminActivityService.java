package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;

public interface AdminActivityService {
    Page<ActivityDTO> getAdminActivityPage(int page, int pageSize,
                                           String id,
                                           String activityName,
                                           String address,
                                           String volunteerHours);
}
