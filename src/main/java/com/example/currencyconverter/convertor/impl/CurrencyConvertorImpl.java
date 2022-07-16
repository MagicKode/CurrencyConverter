package com.example.currencyconverter.convertor.impl;

import com.example.currencyconverter.convertor.CurrencyConvertor;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CurrencyConvertorImpl implements CurrencyConvertor {
    private final CurrencyRepository currencyRepository;

    @Override
    public void convertor(String title, Integer quantity) {


        Currency currency = new Currency();
        currency.setTitle(title);
        currency.setQuantity(quantity);

//    Double result = currency.setQuantity(quantity) * (currencyByTitle1.getQuantity()/currencyByTitle2.getQuantity());
    }
}
