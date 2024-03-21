package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.mapper.ElderActivityDTOMapper;
import com.yaru.TimeBank.service.ElderActivityDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElderActivityDTOServiceImpl implements ElderActivityDTOService {
    @Autowired
    private ElderActivityDTOMapper elderActivityDTOMapper;
    @Override
    public Page<ActivityDTO> getElderActivityPage(int page, int pageSize,
                                                      String id,
                                                      String activityName,
                                                      String volunteerAddress,
                                                      String volunteerHours) {
        Page<RequirementDTO> pageObj = new Page<>(page, pageSize);

        // 调用 Mapper 方法并传入参数进行查询
        // 执行多表连接查询
        IPage<ActivityDTO> resultPage = elderActivityDTOMapper.getActivityPage(pageObj, id, activityName, volunteerAddress, volunteerHours);

        return (Page<ActivityDTO>) resultPage;
    }
}
