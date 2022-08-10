package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.mapper.BankAccountMapper;
import com.example.currencyconverter.model.dto.BankAccountDto;
import com.example.currencyconverter.model.entity.BankAccount;
import com.example.currencyconverter.repository.BankAccountRepository;
import com.example.currencyconverter.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Transactional
    @Override
    public BankAccountDto create(BankAccount bankAccount) {
        return bankAccountMapper.toCheckDto(bankAccountRepository.save(bankAccount));
    }
}
