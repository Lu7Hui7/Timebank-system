package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;

public interface VolunteerActivityDTOService {
    Page<ActivityDTO> getVolunteerActivityPage(int page, int pageSize,
                                              Long volunteerId,
                                              String activityName,
                                              String address,
                                              String volunteerHours,
                                              String id);
}
