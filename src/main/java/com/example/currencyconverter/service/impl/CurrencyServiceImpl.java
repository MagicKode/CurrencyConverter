package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.exception.WrongAmountException;
import com.example.currencyconverter.mapper.CurrencyMapper;
import com.example.currencyconverter.model.dto.CurrencyDto;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.repository.CurrencyRepository;
import com.example.currencyconverter.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CurrencyServiceImpl implements CurrencyService {
    private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    @Autowired
    public CurrencyServiceImpl(
            CurrencyRepository currencyRepository,
            CurrencyMapper currencyMapper
    ) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    @Transactional
    @Override
    public CurrencyDto create(Currency currency) {
        return currencyMapper.toCurrencyDto(currencyRepository.save(currency));
    }

    @Transactional
    @Override
    public void deleteByTitle(String title) {
        currencyRepository.deleteByTitle(title);
        log.info("Deleted currency with title = {}", title);
    }

    @Override
    public List<CurrencyDto> findAll() {
        return currencyMapper.toListCurrencyDto(currencyRepository.findAll());
    }

    @Override
    public Set<String> findExchangeCurrenciesByTitle() {
        return currencyRepository.findRates().stream()
                .map(rate -> rate.getCurrencyFrom().getTitle() + "/" + rate.getCurrencyTo().getTitle())
                .collect(Collectors.toSet());
    }

    @Override
    public Double convertFromTo(String titleFrom, Integer quantityFrom, String titleTo) {
        Double result = 0.00;
        Rate rateByTitle = currencyRepository.findRateByTitle(titleFrom, titleTo);
        if (rateByTitle == null) {
            throw new NotFoundException("No such rate " + titleFrom + "/" + titleTo + " exist!");
        } else if (rateByTitle.getRateValue() == null) {
            throw new WrongAmountException("Such rate " + titleFrom + "/" + titleTo + " is Unavailable");
        } else if (RateType.SELLING_RATE.equals(rateByTitle.getRateType())) {
            result = quantityFrom / rateByTitle.getRateValue() * rateByTitle.getCurrencyTo().getQuantity();
        } else if (RateType.BUYING_RATE.equals(rateByTitle.getRateType())) {
            result = quantityFrom * rateByTitle.getRateValue() / rateByTitle.getCurrencyTo().getQuantity();
        }
        return result;
    }
}
