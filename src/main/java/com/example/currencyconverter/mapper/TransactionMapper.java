package com.example.currencyconverter.mapper;

import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
    TransactionDto toTransactionDto(Transaction transaction);
}
