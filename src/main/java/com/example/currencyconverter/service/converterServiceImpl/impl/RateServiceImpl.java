package com.example.currencyconverter.service.converterServiceImpl.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.mapper.RateMapper;
import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.repository.CurrencyRepository;
import com.example.currencyconverter.repository.RateRepository;
import com.example.currencyconverter.service.converterServiceImpl.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final RateMapper rateMapper;

    @Transactional
    @Override
    public RateDto create(Rate rate) {
        return rateMapper.toRateDto(rateRepository.save(rate));
    }

    @Transactional
    @Override
    public RateDto update(Rate rate) {
        Rate rateFromDb = rateRepository.findById(
                rate.getId()).orElseThrow(
                () -> new NotFoundException("No rate found with such id = " + rate.getId())
        );
        rateFromDb.setRateValue(rate.getRateValue());
        rateFromDb.setRateType(rate.getRateType());
        rateFromDb.setCurrencyFrom(rate.getCurrencyFrom());
        rateFromDb.setCurrencyTo(rate.getCurrencyTo());
        return rateMapper.toRateDto(rateRepository.save(rateFromDb));
    }

    @Override
    public List<RateDto> findAllRatesByTitle(String title) {
        List<Rate> byCurrencyTitle = rateRepository.findByCurrencyTitle(title);
        return rateMapper.toListRateDto(byCurrencyTitle);
    }

    @Override
    public List<RateDto> findBuyingSellingRatesByTitle(RateType rateType, String title) {
        return rateMapper.toListRateDto(rateRepository.findAllRatesByTitle(rateType, title));
    }

    @Override
    public List<RateDto> findConversionRatesByTitle(RateType rateType, String title) {
        return rateMapper.toListRateDto(rateRepository.findAllConversionRatesByTitle(rateType, title));
    }

    @Override
    public List<RateDto> findAllRates() {
        return rateMapper.toListRateDto(rateRepository.findAll());
    }

    @Override
    public List<RateDto> updateListOfRates (List<Rate> rates) {
       /* List<RateDto> rateFromDb = rateRepository.findListOfRatesByTitle(
                rates.get).orElseThrow(
                () -> new NotFoundException("No rates found with such id = " + rate.getId())
        );*/
        List<RateDto> rateFromDb = rateRepository.findListOfRatesByTitle()

        rateFromDb.setRateValue(rate.getRateValue());
        rateFromDb.setRateType(rate.getRateType());
        rateFromDb.setCurrencyFrom(rate.getCurrencyFrom());
        rateFromDb.setCurrencyTo(rate.getCurrencyTo());
        rates.add(rateFromDb);
        return rateMapper.toListRateDto(rateRepository.save(rates));
        return null;
    }
}
