package com.example.currencyconverter.mapper;

import com.example.currencyconverter.model.dto.AccountDto;
import com.example.currencyconverter.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    AccountDto toAccountDto(Account account);
}
