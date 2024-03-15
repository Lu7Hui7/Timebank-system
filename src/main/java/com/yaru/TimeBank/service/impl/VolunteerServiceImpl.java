package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.entity.Volunteer;
import com.yaru.TimeBank.mapper.VolunteerMapper;
import com.yaru.TimeBank.service.VolunteerService;
import org.springframework.stereotype.Service;

@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {
}
