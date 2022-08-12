package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.mapper.AccountMapper;
import com.example.currencyconverter.model.dto.AccountDto;
import com.example.currencyconverter.model.entity.Account;
import com.example.currencyconverter.repository.AccountRepository;
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
class AccountServiceImplTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountMapper accountMapper;

    @InjectMocks
    AccountServiceImpl testSubject;

    private Account createAccount(Long id, String title, int user_id) {
        Account account = new Account();
        account.setId(id);
        account.setTitle(title);
        account.setUser_id(user_id);
        return account;
    }

    private AccountDto createAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setTitle(account.getTitle());
        accountDto.setUser_id(account.getUser_id());
        return accountDto;
    }

    @Test
    void shouldCreate() {
        //given
        Account account = createAccount(1L, "First", 1);
        AccountDto accountDto = createAccountDto(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountDto(account)).thenReturn(accountDto);
        //when
        AccountDto result = testSubject.create(account);
        //then
        Assertions.assertEquals(account.getTitle(), result.getTitle());
        verify(accountRepository, times(1)).save(account);
        verify(accountMapper, times(1)).toAccountDto(account);
    }
}
