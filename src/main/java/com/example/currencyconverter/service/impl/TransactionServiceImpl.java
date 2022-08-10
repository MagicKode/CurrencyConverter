package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.mapper.TransactionMapper;
import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Transaction;
import com.example.currencyconverter.repository.TransactionRepository;
import com.example.currencyconverter.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public TransactionDto create(Transaction transaction) {
        return transactionMapper.toTransactionDto(transactionRepository.save(transaction));
    }
}
