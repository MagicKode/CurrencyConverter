package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.mapper.TransactionMapper;
import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.BankAccount;
import com.example.currencyconverter.model.entity.Transaction;
import com.example.currencyconverter.model.entity.enums.OperationType;
import com.example.currencyconverter.repository.BankAccountRepository;
import com.example.currencyconverter.repository.RateRepository;
import com.example.currencyconverter.repository.TransactionRepository;
import com.example.currencyconverter.service.CurrencyService;
import com.example.currencyconverter.service.TransactionService;
import com.example.currencyconverter.service.converter.RateValueConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final BankAccountRepository bankAccountRepository;
    private final RateRepository rateRepository;
    private final CurrencyService currencyService;
    private final RateValueConverter rateValueConverter;

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

    @Transactional
    @Override
    public String currencyTransfer(String titleFrom, String titleTo, Integer sum, Long accountId) {
        BankAccount bankAccountFrom = bankAccountRepository.findBankAccountByCurrencyTitleAndAccount_Id(titleFrom, accountId);
        if (bankAccountFrom == null) {
            throw new NotFoundException("No bankAccount found with such title = " + titleFrom);
        }
        BankAccount bankAccountTo = bankAccountRepository.findBankAccountByCurrencyTitleAndAccount_Id(titleTo, accountId);
        if (bankAccountTo == null) {
            throw new NotFoundException("No bankAccount found with such title = " + titleTo);
        }
        Double convertFromTo = currencyService.convertFromTo(titleFrom, sum, titleTo);
        Integer result = rateValueConverter.convertToDatabaseColumn(convertFromTo);
        Transaction transactionFrom = createTransaction(sum, bankAccountFrom, OperationType.DECREMENT);
        operations(transactionFrom);
        Transaction transactionTo = createTransaction(result, bankAccountTo, OperationType.INCREMENT);
        operations(transactionTo);
        return "Success! " + "\n"
                + "Transfer sum: " + sum + " " + transactionFrom.getBankAccount().getCurrency().getTitle() + "." + "\n"
                + "From bankAccount_1 was decrement: " + sum + " " + transactionFrom.getBankAccount().getCurrency().getTitle() + "\n"
                + "In bankAccount_2 was increment: " + result + " " + transactionTo.getBankAccount().getCurrency().getTitle() + ".";
    }
    private Transaction createTransaction(Integer sum, BankAccount bankAccount, OperationType operationType) {
        Transaction transaction = new Transaction();
        transaction.setTransactionSum(sum);
        transaction.setBankAccount(bankAccount);
        transaction.setOperationType(operationType);
        return transaction;
    }
}
