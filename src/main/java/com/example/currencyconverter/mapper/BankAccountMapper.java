package com.example.currencyconverter.mapper;

import com.example.currencyconverter.model.dto.BankAccountDto;
import com.example.currencyconverter.model.entity.BankAccount;
import org.mapstruct.Mapper;

@Mapper
public interface BankAccountMapper {
    BankAccountDto toBankAccountDto(BankAccount bankAccount);
}
