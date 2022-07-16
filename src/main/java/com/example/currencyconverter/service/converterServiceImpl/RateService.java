package com.example.currencyconverter.service.converterServiceImpl;

import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;

import java.util.List;
import java.util.Map;

public interface RateService {

    RateDto create(Rate rate);
    RateDto update(Rate rate);

    List<RateDto> findAllRatesByTitle(String title);
    List<RateDto> findBuyingSellingRatesByTitle(RateType rateType, String title);
    List<RateDto> findConversionRatesByTitle(RateType rateType, String title);
    List<RateDto> findAllRates();
    List<RateDto> updateListOfRates(List<Rate> rates);

    Map<String, String> convertCurrencies(String title, RateType rateType);



}
