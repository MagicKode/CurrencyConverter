package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.mapper.AccountMapper;
import com.example.currencyconverter.model.dto.AccountDto;
import com.example.currencyconverter.model.entity.Account;
import com.example.currencyconverter.repository.AccountRepository;
import com.example.currencyconverter.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public AccountDto create(Account account) {
        return accountMapper.toAccountDto(accountRepository.save(account));
    }
}
