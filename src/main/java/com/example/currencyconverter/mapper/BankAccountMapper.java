package com.example.currencyconverter.mapper;

import com.example.currencyconverter.model.dto.BankAccountDto;
import org.mapstruct.Mapper;

@Mapper
public interface BankAccountMapper {
    BankAccountDto toBankAccountDto(com.example.currencyconverter.model.entity.BankAccount bankAccount);
}
