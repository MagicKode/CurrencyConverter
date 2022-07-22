package com.example.currencyconverter.service;

import com.example.currencyconverter.model.dto.MessageDto;
import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;

import java.util.List;

public interface RateService {

    RateDto create(Rate rate);
    RateDto update(Rate rate);
    RateDto createConversionRate(Rate rate);
    MessageDto updateListOfRatesModify(List<Rate> rates);

    List<RateDto> findAllRatesByTitle(String title);
    List<RateDto> findBuyingSellingRatesByTitle(RateType rateType, String title);
    List<RateDto> findConversionRatesByTitle(RateType rateType, String title);
    List<RateDto> findAllRates();

    boolean updateListOfRates(List<Rate> rates);
}
