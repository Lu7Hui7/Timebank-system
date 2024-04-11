package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.entity.AOrder;
import com.yaru.TimeBank.mapper.AOrderMapper;
import com.yaru.TimeBank.service.AOrderService;
import org.springframework.stereotype.Service;

@Service
public class AOrderServiceImpl extends ServiceImpl<AOrderMapper, AOrder> implements AOrderService {
}
