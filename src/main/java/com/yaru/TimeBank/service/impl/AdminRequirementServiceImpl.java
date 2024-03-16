package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.dto.AdminRequirementDTO;
import com.yaru.TimeBank.entity.Requirement;
import com.yaru.TimeBank.mapper.AdminRequirementMapper;
import com.yaru.TimeBank.service.AdminRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRequirementServiceImpl implements AdminRequirementService {
    @Autowired
    private AdminRequirementMapper adminRequirementMapper;

    @Override
    public Page<AdminRequirementDTO> getAdminRequirementPage(int page, int pageSize, String name) {
        // 构造分页对象
        Page<AdminRequirementDTO> pageInfo = new Page<>(page, pageSize);

        // 执行多表连接查询
        IPage<AdminRequirementDTO> resultPage = adminRequirementMapper.selectVolunteerRequestPage(pageInfo, name);

        return (Page<AdminRequirementDTO>) resultPage;
    }


}
