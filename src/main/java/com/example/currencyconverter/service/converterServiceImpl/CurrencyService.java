package com.example.currencyconverter.service.converterServiceImpl;

import com.example.currencyconverter.model.dto.CurrencyDto;
import com.example.currencyconverter.model.entity.Currency;

import java.util.List;
import java.util.Set;

public interface CurrencyService {
    CurrencyDto create(Currency currency);
    Integer convertFromTo(String titleFrom, Integer quantityFrom, String titleTo);

    void deleteByTitle(String title);

    List<CurrencyDto> findAll();
    Set<String> findExchangeCurrenciesByTitle();
}
