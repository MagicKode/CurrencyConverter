package com.example.currencyconverter.service;

import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Transaction;

public interface TransactionService {
    TransactionDto operations(Transaction transaction);
    String currencyTransfer(String titleFrom, String titleTo, Integer sum, Long accountId);
}
