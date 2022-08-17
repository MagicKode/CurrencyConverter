package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.mapper.TransactionMapper;
import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Account;
import com.example.currencyconverter.model.entity.BankAccount;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.Transaction;
import com.example.currencyconverter.model.entity.enums.OperationType;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.repository.BankAccountRepository;
import com.example.currencyconverter.repository.TransactionRepository;
import com.example.currencyconverter.service.CurrencyService;
import com.example.currencyconverter.service.converter.RateValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.currencyconverter.model.entity.enums.OperationType.DECREMENT;
import static com.example.currencyconverter.model.entity.enums.OperationType.INCREMENT;
import static org.mockito.ArgumentMatchers.any;
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
    CurrencyService currencyService;
    @Mock
    RateValueConverter rateValueConverter;
    @Mock
    TransactionMapper transactionMapper;
    @InjectMocks
    TransactionServiceImpl testSubject;

    private Currency createCurrency(Long id, String title, String meaning, Integer quantity) {
        Currency currency = new Currency();
        currency.setId(id);
        currency.setTitle(title);
        currency.setMeaning(meaning);
        currency.setQuantity(quantity);
        return currency;
    }

    private Rate createRate(Long id, Double value, RateType rateType, Currency currencyFrom, Currency currencyTo) {
        Rate rate = new Rate();
        rate.setId(id);
        rate.setRateValue(value);
        rate.setRateType(rateType);
        rate.setCurrencyFrom(currencyFrom);
        rate.setCurrencyTo(currencyTo);
        return rate;
    }

    private Account createAccount(Long id, String title, int user_id) {
        Account account = new Account();
        account.setId(id);
        account.setTitle(title);
        account.setUser_id(user_id);
        return account;
    }

    private BankAccount createBankAccount(Long id, Currency currency, int user_id, Integer value, Account account) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setUser_id(user_id);
        bankAccount.setCurrency(currency);
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
        Currency currency = createCurrency(1L, "USD", null, 500);
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, currency, 1, 100, account);
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
        Currency currency = createCurrency(1L, "USD", null, 500);
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, currency, 1, 500, account);
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
        Currency currency = createCurrency(1L, "USD", null, 500);
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, currency, 1, 999_999_999, account);
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
        Currency currency = createCurrency(1L, "USD", null, 500);
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, currency, 1, 90, account);
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

    @Test
    void shouldThrowExceptionWhenBankAccountFromNotFound() {
        //given
        String titleFrom = "USD";
        String titleTo = "BYN";
        Integer sum = 20;
        Long accountId = 1L;
        Currency currencyFrom = createCurrency(1L, "USD", null, 10);
        Currency currencyTo = createCurrency(2L, "BYN", null, 100);
        Rate rateFromDb = createRate(1L, 0.2d, RateType.SELLING_RATE, currencyFrom, currencyTo);
        String expectedMessage = "No bankAccount found with such title = " + titleFrom;
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.currencyTransfer(titleFrom, titleTo, sum, accountId));
        //then
        Assertions.assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBankAccountToNotFound() {
        //given
        String titleFrom = "USD";
        String titleTo = "BYN";
        Integer sum = 20;
        Long accountId = 1L;
        Currency currencyFrom = createCurrency(1L, "USD", null, 10);
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccountFrom = createBankAccount(1L, currencyFrom, 1, 1000, account);
        when(bankAccountRepository.findBankAccountByCurrencyTitleAndAccount_Id("USD", 1L)).thenReturn(bankAccountFrom);
        String expectedMessage = "No bankAccount found with such title = " + titleTo;
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.currencyTransfer(titleFrom, titleTo, sum, accountId));
        //then
        Assertions.assertEquals(expectedMessage, result.getMessage());
        verify(bankAccountRepository, times(1)).findBankAccountByCurrencyTitleAndAccount_Id(titleFrom, accountId);
    }

    @Test
    void shouldPerformTransfer() {
        //given
        String titleFrom = "EUR";
        String titleTo = "BYN";
        Integer sum = 200;
        Long accountId = 1L;
        Currency currencyFrom = createCurrency(1L, "EUR", null, 10);
        Currency currencyTo = createCurrency(2L, "BYN", null, 100);
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccountFrom = createBankAccount(1L, currencyFrom, 1, 1000, account);
        BankAccount bankAccountTo = createBankAccount(2L, currencyTo, 1, 2000, account);
        when(bankAccountRepository.save(bankAccountFrom)).thenReturn(bankAccountFrom);
        when(bankAccountRepository.save(bankAccountTo)).thenReturn(bankAccountTo);
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccountFrom));
        when(bankAccountRepository.findById(2L)).thenReturn(Optional.of(bankAccountTo));
        when(bankAccountRepository.findBankAccountByCurrencyTitleAndAccount_Id("EUR", 1L)).thenReturn(bankAccountFrom);
        when(bankAccountRepository.findBankAccountByCurrencyTitleAndAccount_Id("BYN", 1L)).thenReturn(bankAccountTo);
        when(currencyService.convertFromTo(titleFrom, sum, titleTo)).thenReturn(200.0);
        when(rateValueConverter.convertToDatabaseColumn(200.0)).thenReturn(2);
        Integer expectedDecrementResult = 800;
        Integer expectedIncrementResult = 2002;
        String expectedMessage = "Success! " + "\n"
                + "Transfer sum: " + sum + " " + titleFrom + "." + "\n"
                + "From bankAccount_1 was decrement: " + sum + " " + titleFrom + "\n"
                + "In bankAccount_2 was increment: " + 2 + " " + titleTo + ".";
        //when
        String result = testSubject.currencyTransfer(titleFrom, titleTo, sum, accountId);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedMessage, result);
        Assertions.assertEquals(expectedDecrementResult, bankAccountFrom.getCurrencyValue());
        Assertions.assertEquals(expectedIncrementResult, bankAccountTo.getCurrencyValue());
        verify(bankAccountRepository, times(1)).save(bankAccountFrom);
        verify(bankAccountRepository, times(1)).save(bankAccountTo);
        verify(bankAccountRepository, times(1)).findBankAccountByCurrencyTitleAndAccount_Id(titleFrom, accountId);
        verify(bankAccountRepository, times(1)).findBankAccountByCurrencyTitleAndAccount_Id(titleTo, accountId);
        verify(currencyService, times(1)).convertFromTo(titleFrom, sum, titleTo);
        verify(rateValueConverter, times(1)).convertToDatabaseColumn(200.0);
        verify(transactionRepository, times(2)).save(any());
        verify(transactionMapper, times(2)).toTransactionDto(any());
    }
}
