package com.example.currencyconverter.mapper;

import com.example.currencyconverter.model.dto.CurrencyDto;
import com.example.currencyconverter.model.entity.Currency;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CurrencyMapper {
    CurrencyDto toCurrencyDto(Currency currency);
    List<CurrencyDto> toListCurrencyDto(List<Currency> currencies);
}
