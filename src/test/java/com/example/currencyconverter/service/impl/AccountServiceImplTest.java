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

    @Test
    void shouldCreate() {
        //given
        String title = "Account";
        Account account = new Account();
        account.setId(1L);
        account.setTitle(title);
        account.setUser_id(1);
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setTitle(account.getTitle());
        accountDto.setUser_id(account.getUser_id());
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountDto(account)).thenReturn(accountDto);
        //when
        AccountDto result = testSubject.create(account);
        //then
        Assertions.assertEquals(title, result.getTitle());
        verify(accountRepository, times(1)).save(account);
        verify(accountMapper, times(1)).toAccountDto(account);
    }
}