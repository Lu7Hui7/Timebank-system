package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.entity.Activity;
import com.yaru.TimeBank.mapper.ActivityMapper;
import com.yaru.TimeBank.service.ActivityService;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
}
