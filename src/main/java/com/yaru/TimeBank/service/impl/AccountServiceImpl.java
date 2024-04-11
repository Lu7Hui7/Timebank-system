package com.yaru.TimeBank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaru.TimeBank.entity.Account;
import com.yaru.TimeBank.mapper.AccountMapper;
import com.yaru.TimeBank.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
}
