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
    public Page<AdminActivityDTO> getAdminActivityPage(int page, int pageSize,
                                                String id,
                                                String activityName,
                                                String address,
                                                String volunteerHours){
        Page<AdminActivityDTO> pageInfo = new Page<>(page, pageSize);

        // 调用 Mapper 方法并传入参数进行查询
        // 执行多表连接查询
        IPage<AdminActivityDTO> resultPage = adminActivityMapper.getAdminActivityPage(pageInfo, id,activityName, address, volunteerHours);

        return (Page<AdminActivityDTO>) resultPage;
    }
}
