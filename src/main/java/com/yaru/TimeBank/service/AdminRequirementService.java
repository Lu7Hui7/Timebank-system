package com.yaru.TimeBank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaru.TimeBank.dto.AdminRequirementDTO;
import com.yaru.TimeBank.entity.Requirement;

import java.util.Map;

public interface AdminRequirementService {
    Page<AdminRequirementDTO> getAdminRequirementPage(int page, int pageSize,
                                                      String serviceName,
                                                      String address,
                                                      String durationHours,
                                                      String id);

}
