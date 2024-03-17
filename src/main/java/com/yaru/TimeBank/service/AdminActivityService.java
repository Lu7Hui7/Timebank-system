package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.AdminActivityDTO;
import com.yaru.TimeBank.dto.AdminRequirementDTO;

public interface AdminActivityService {
    Page<AdminActivityDTO> getAdminActivityPage(int page, int pageSize, String name);
}
