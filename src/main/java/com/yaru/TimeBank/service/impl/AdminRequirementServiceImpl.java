package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.dto.AdminActivityDTO;
import com.yaru.TimeBank.dto.AdminRequirementDTO;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.mapper.AdminRequirementMapper;
import com.yaru.TimeBank.service.AdminRequirementService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminRequirementServiceImpl implements AdminRequirementService {
    @Autowired
    private AdminRequirementMapper adminRequirementMapper;

    @Override
    public Page<AdminRequirementDTO> getAdminRequirementPage(int page, int pageSize,
                                                             String serviceName,
                                                             String address,
                                                             String durationHours,
                                                             String id) {
        Page<AdminRequirementDTO> pageObj = new Page<>(page, pageSize);

        // 调用 Mapper 方法并传入参数进行查询
        // 执行多表连接查询
        IPage<AdminRequirementDTO> resultPage = adminRequirementMapper.getElderRequestPage(pageObj, serviceName, address, durationHours, id);

        return (Page<AdminRequirementDTO>) resultPage;
    }
}
