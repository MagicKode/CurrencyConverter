package com.example.currencyconverter.service;

import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Transaction;

public interface TransactionService {
    TransactionDto operations(Transaction transaction);
}
