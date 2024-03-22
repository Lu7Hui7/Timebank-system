package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.mapper.VolunteerRequirementDTOMapper;
import com.yaru.TimeBank.service.VolunteerRequirementDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerRequirementDTOServiceImpl implements VolunteerRequirementDTOService {

    @Autowired
    private VolunteerRequirementDTOMapper volunteerRequirementDTOMapper;
    @Override
    public Page<RequirementDTO> volunteerGetRequirementPage(int page, int pageSize, String id, String serviceName, String address, String durationHours) {
        Page<RequirementDTO> pageObj = new Page<>(page, pageSize);

        // 调用 Mapper 方法并传入参数进行查询
        // 执行多表连接查询
        IPage<RequirementDTO> resultPage = volunteerRequirementDTOMapper.volunteerGetRequestPage(pageObj,id, serviceName, address, durationHours);

        return (Page<RequirementDTO>) resultPage;
    }
}
