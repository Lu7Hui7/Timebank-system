package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.mapper.VolunteerActivityDTOMapper;
import com.yaru.TimeBank.service.VolunteerActivityDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerActivityDTOServiceImpl implements VolunteerActivityDTOService {

    @Autowired
    private VolunteerActivityDTOMapper volunteerActivityDTOMapper;
    @Override
    public Page<ActivityDTO> getVolunteerActivityPage(int page, int pageSize,
                                                      int volunteerId,
                                                      String activityName,
                                                      String address,
                                                      String volunteerHours,
                                                      String id) {
        Page<ActivityDTO> pageObj = new Page<>(page, pageSize);

        // 调用 Mapper 方法并传入参数进行查询
        // 执行多表连接查询
        IPage<ActivityDTO> resultPage = volunteerActivityDTOMapper.getVolunteerActivityPage(pageObj, volunteerId, activityName, address, volunteerHours, id);

        return (Page<ActivityDTO>) resultPage;
    }
}
