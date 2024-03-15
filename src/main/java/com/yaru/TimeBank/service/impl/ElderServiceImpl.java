package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.entity.Elder;
import com.yaru.TimeBank.mapper.ElderMapper;
import com.yaru.TimeBank.service.ElderService;
import org.springframework.stereotype.Service;

@Service
public class ElderServiceImpl extends ServiceImpl<ElderMapper, Elder> implements ElderService {
}
