package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.AdminActivityDTO;
import com.yaru.TimeBank.dto.AdminRequirementDTO;
import com.yaru.TimeBank.mapper.AdminActivityMapper;
import com.yaru.TimeBank.mapper.AdminRequirementMapper;
import com.yaru.TimeBank.service.AdminActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminActivityServiceImpl implements AdminActivityService {
    @Autowired
    private AdminActivityMapper adminActivityMapper;

    @Override
    public Page<AdminActivityDTO> getAdminActivityPage(int page, int pageSize, String name) {
        // 构造分页对象
        Page<AdminActivityDTO> pageInfo = new Page<>(page, pageSize);

        // 执行多表连接查询
        IPage<AdminActivityDTO> resultPage = adminActivityMapper.selectVolunteerActivityPage(pageInfo, name);

        return (Page<AdminActivityDTO>) resultPage;
    }


}
