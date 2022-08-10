package com.example.currencyconverter.service;

import com.example.currencyconverter.model.dto.BankAccountDto;

public interface BankAccountService {
    BankAccountDto create(com.example.currencyconverter.model.entity.BankAccount bankAccount);
}
