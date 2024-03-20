package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.mapper.AdminRequirementDTOMapper;
import com.yaru.TimeBank.service.AdminRequirementDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRequirementDTOServiceImpl implements AdminRequirementDTOService {
    @Autowired
    private AdminRequirementDTOMapper adminRequirementDTOMapper;

    @Override
    public Page<RequirementDTO> getAdminRequirementPage(int page, int pageSize,
                                                        String serviceName,
                                                        String address,
                                                        String durationHours,
                                                        String id) {
        Page<RequirementDTO> pageObj = new Page<>(page, pageSize);

        // 调用 Mapper 方法并传入参数进行查询
        // 执行多表连接查询
        IPage<RequirementDTO> resultPage = adminRequirementDTOMapper.getElderRequestPage(pageObj, serviceName, address, durationHours, id);

        return (Page<RequirementDTO>) resultPage;
    }
}
