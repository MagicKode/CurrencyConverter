package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.mapper.TransactionMapper;
import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.BankAccount;
import com.example.currencyconverter.model.entity.Transaction;
import com.example.currencyconverter.model.entity.enums.OperationType;
import com.example.currencyconverter.repository.BankAccountRepository;
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
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    @Override
    public TransactionDto operations(Transaction transaction) {
        int maxValue = 1_000_000_000;
        int minValue = 0;
        BankAccount bankAccount = bankAccountRepository.findById(transaction.getBankAccount().getId()).orElseThrow(
                () -> new NotFoundException("No bankAccount found with such id = " + transaction.getBankAccount().getId())
        );
        if (OperationType.INCREMENT.equals(transaction.getOperationType())
                && (bankAccount.getCurrencyValue() + transaction.getTransactionSum()) <= maxValue
        ) {
            bankAccount.setCurrencyValue(bankAccount.getCurrencyValue() + transaction.getTransactionSum());
        } else if (OperationType.DECREMENT.equals(transaction.getOperationType())
                && (bankAccount.getCurrencyValue() - transaction.getTransactionSum()) >= minValue
        ) {
            bankAccount.setCurrencyValue(bankAccount.getCurrencyValue() - transaction.getTransactionSum());
        } else {
            throw new RuntimeException("Transaction Error");
        }
        bankAccountRepository.save(bankAccount);
        return transactionMapper.toTransactionDto(transactionRepository.save(transaction));
    }
}
