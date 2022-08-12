package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.mapper.TransactionMapper;
import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Account;
import com.example.currencyconverter.model.entity.BankAccount;
import com.example.currencyconverter.model.entity.Transaction;
import com.example.currencyconverter.model.entity.enums.OperationType;
import com.example.currencyconverter.repository.BankAccountRepository;
import com.example.currencyconverter.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.currencyconverter.model.entity.enums.OperationType.DECREMENT;
import static com.example.currencyconverter.model.entity.enums.OperationType.INCREMENT;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    BankAccountRepository bankAccountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    TransactionMapper transactionMapper;

    @InjectMocks
    TransactionServiceImpl testSubject;

    private Account createAccount(Long id, String title, int user_id) {
        Account account = new Account();
        account.setId(id);
        account.setTitle(title);
        account.setUser_id(user_id);
        return account;
    }

    private BankAccount createBankAccount(Long id, String currencyTitle, int user_id, Integer value, Account account) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setUser_id(user_id);
        bankAccount.setCurrencyTitle(currencyTitle);
        bankAccount.setCurrencyValue(value);
        bankAccount.setAccount(account);
        return bankAccount;
    }

    private Transaction createTransaction(
            Long id,
            Integer transactionSum,
            OperationType operationType,
            BankAccount bankAccount
    ) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setOperationType(operationType);
        transaction.setTransactionSum(transactionSum);
        transaction.setBankAccount(bankAccount);
        return transaction;
    }

    private TransactionDto createTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setTransactionSum(transaction.getTransactionSum());
        transactionDto.setOperationType(transaction.getOperationType());
        return transactionDto;
    }

    @Test
    void shouldPerformOperationIncrement() {
        //given
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, "USD", 1, 100, account);
        Transaction transaction = createTransaction(1L, 100, INCREMENT, bankAccount);
        TransactionDto transactionDto = createTransactionDto(transaction);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toTransactionDto(transaction)).thenReturn(transactionDto);
        Integer expectedResult = 200;
        //when
        TransactionDto result = testSubject.operations(transaction);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, bankAccount.getCurrencyValue());
        verify(bankAccountRepository, times(1)).findById(transaction.getBankAccount().getId());
        verify(bankAccountRepository, times(1)).save(bankAccount);
        verify(transactionRepository, times(1)).save(transaction);
        verify(transactionMapper, times(1)).toTransactionDto(transaction);
    }

    @Test
    void shouldPerformOperationDecrement() {
        //given
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, "USD", 1, 500, account);
        Transaction transaction = createTransaction(1L, 100, DECREMENT, bankAccount);
        TransactionDto transactionDto = createTransactionDto(transaction);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toTransactionDto(transaction)).thenReturn(transactionDto);
        Integer expectedResult = 400;
        //when
        TransactionDto result = testSubject.operations(transaction);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, bankAccount.getCurrencyValue());
        verify(bankAccountRepository, times(1)).findById(transaction.getBankAccount().getId());
        verify(bankAccountRepository, times(1)).save(bankAccount);
        verify(transactionRepository, times(1)).save(transaction);
        verify(transactionMapper, times(1)).toTransactionDto(transaction);
    }

    @Test
    void shouldNotPerformOperationIncrementWhenValueGreaterThanMaxValue() {
        //given
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, "USD", 1, 999_999_999, account);
        Transaction transaction = createTransaction(1L, 100, INCREMENT, bankAccount);
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        String expectedMessage = "Transaction Error";
        //when
        RuntimeException result = Assertions
                .assertThrows(RuntimeException.class, () -> testSubject.operations(transaction));
        //then
        Assertions.assertEquals(result.getMessage(), expectedMessage);
        verify(bankAccountRepository, times(1)).findById(transaction.getBankAccount().getId());
    }

    @Test
    void shouldNotPerformOperationDecrementWhenValueLessThanMinValue() {
        //given
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, "USD", 1, 90, account);
        Transaction transaction = createTransaction(1L, 100, DECREMENT, bankAccount);
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        String expectedMessage = "Transaction Error";
        //when
        RuntimeException result = Assertions
                .assertThrows(RuntimeException.class, () -> testSubject.operations(transaction));
        //then
        Assertions.assertEquals(result.getMessage(), expectedMessage);
        verify(bankAccountRepository, times(1)).findById(1L);
    }

    @Test
    void shouldNotPerformOperationIncrementWhenNoBankAccountFound() {
        //given
        BankAccount bankAccount = new BankAccount();
        Transaction transaction = createTransaction(1L, 100, INCREMENT, bankAccount);
        String expectedMessage = "No bankAccount found with such id = " + transaction.getBankAccount().getId();
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.operations(transaction));
        //then
        Assertions.assertEquals(result.getMessage(), expectedMessage);
    }

    @Test
    void shouldNotPerformOperationDecrementWhenNoBankAccountFound() {
        //given
        BankAccount bankAccount = new BankAccount();
        Transaction transaction = createTransaction(1L, 100, DECREMENT, bankAccount);
        String expectedMessage = "No bankAccount found with such id = " + transaction.getBankAccount().getId();
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.operations(transaction));
        //then
        Assertions.assertEquals(result.getMessage(), expectedMessage);
    }
}
