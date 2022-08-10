package com.example.currencyconverter.service;

import com.example.currencyconverter.model.dto.AccountDto;
import com.example.currencyconverter.model.entity.Account;

public interface AccountService {
    AccountDto create(Account account);
}
