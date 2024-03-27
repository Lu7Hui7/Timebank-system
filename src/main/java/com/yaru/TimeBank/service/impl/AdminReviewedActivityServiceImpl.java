package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import com.yaru.TimeBank.dto.RequirementDTO;
import com.yaru.TimeBank.mapper.AdminReviewedActivityMappper;
import com.yaru.TimeBank.service.AdminReviewedActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminReviewedActivityServiceImpl implements AdminReviewedActivityService {
    @Autowired
    private AdminReviewedActivityMappper adminReviewedActivityMappper;

    @Override
    public Page<ActivityDTO> getReviewedActivityPage(int page, int pageSize, String id, String activityName, String address, String volunteerHours) {
        Page<ActivityDTO> pageInfo = new Page<>(page, pageSize);

        // 调用 Mapper 方法并传入参数进行查询
        // 执行多表连接查询
        IPage<ActivityDTO> resultPage = adminReviewedActivityMappper.getReviewedActivityPage(pageInfo, id,activityName, address, volunteerHours);

        return (Page<ActivityDTO>) resultPage;
    }
}
