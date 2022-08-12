package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.mapper.BankAccountMapper;
import com.example.currencyconverter.model.dto.BankAccountDto;
import com.example.currencyconverter.model.entity.Account;
import com.example.currencyconverter.model.entity.BankAccount;
import com.example.currencyconverter.repository.BankAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {
    @Mock
    BankAccountRepository bankAccountRepository;
    @Mock
    BankAccountMapper bankAccountMapper;

    @InjectMocks
    BankAccountServiceImpl testSubject;

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

    private BankAccountDto createBankAccountDto(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(bankAccount.getId());
        bankAccountDto.setCurrencyTitle(bankAccount.getCurrencyTitle());
        bankAccountDto.setValue(bankAccount.getCurrencyValue());
        return bankAccountDto;
    }

    @Test
    void shouldCreate() {
        //given
        Account account = createAccount(1L, "First", 1);
        BankAccount bankAccount = createBankAccount(1L, "USD", 1, 100, account);
        BankAccountDto bankAccountDto = createBankAccountDto(bankAccount);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        when(bankAccountMapper.toBankAccountDto(bankAccount)).thenReturn(bankAccountDto);
        //when
        BankAccountDto result = testSubject.create(bankAccount);
        //then
        Assertions.assertEquals(bankAccount.getCurrencyTitle(), result.getCurrencyTitle());
        verify(bankAccountRepository, times(1)).save(bankAccount);
        verify(bankAccountMapper, times(1)).toBankAccountDto(bankAccount);
    }
}
