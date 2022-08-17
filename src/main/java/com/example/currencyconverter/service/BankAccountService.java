package com.example.currencyconverter.service;

import com.example.currencyconverter.model.dto.BankAccountDto;
import com.example.currencyconverter.model.entity.BankAccount;

public interface BankAccountService {
    BankAccountDto create(BankAccount bankAccount);
}
