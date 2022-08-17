package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.exception.WrongAmountException;
import com.example.currencyconverter.mapper.CurrencyMapper;
import com.example.currencyconverter.model.dto.CurrencyDto;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.repository.CurrencyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    @Mock
    CurrencyRepository currencyRepository;
    @Mock
    CurrencyMapper currencyMapper;
    @InjectMocks
    CurrencyServiceImpl testSubject;

    private Currency createCurrency(Long id, String title, String meaning, Integer quantity) {
        Currency currency = new Currency();
        currency.setId(id);
        currency.setTitle(title);
        currency.setMeaning(meaning);
        currency.setQuantity(quantity);
        return currency;
    }

    private CurrencyDto createCurrencyDto(Currency currency) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(currency.getId());
        currencyDto.setTitle(currency.getTitle());
        currencyDto.setMeaning(currency.getMeaning());
        currencyDto.setQuantity(currency.getQuantity());
        return currencyDto;
    }

    private Rate createRate(Long id, Double rateValue, RateType rateType, Currency currencyFrom, Currency currencyTo) {
        Rate rate = new Rate();
        rate.setId(id);
        rate.setRateValue(rateValue);
        rate.setRateType(rateType);
        rate.setCurrencyFrom(currencyFrom);
        rate.setCurrencyTo(currencyTo);
        return rate;
    }

    @Test
    void shouldCreate() {
        //given
        String title = "USD";
        Currency currency = createCurrency(1L, "USD", null, 10);
        CurrencyDto currencyDto = createCurrencyDto(currency);
        when(currencyRepository.save(currency)).thenReturn(currency);
        when(currencyMapper.toCurrencyDto(currency)).thenReturn(currencyDto);
        //when
        CurrencyDto result = testSubject.create(currency);
        //then
        Assertions.assertEquals(title, result.getTitle());
        verify(currencyRepository, times(1)).save(currency);
        verify(currencyMapper, times(1)).toCurrencyDto(currency);

    }

    @Test
    void shouldDeleteByTitle() {
        //given
        String title = "USD";
        //when
        testSubject.deleteByTitle(title);
        //then
        verify(currencyRepository, times(1)).deleteByTitle(title);
    }

    @Test
    void shouldFindExchangeCurrenciesByTitle() {
        //given
        String usd = "USD", byn = "BYN";
        String expectedResult = usd + "/" + byn;
        Currency currencyUSD = createCurrency(1L, "USD", null, 10);
        Currency currencyBYN = createCurrency(1L, "BYN", null, 10);
        Rate rate = createRate(1L, 2D, RateType.SELLING_RATE, currencyUSD, currencyBYN);
        List<Rate> rates = Collections.singletonList(rate);
        when(currencyRepository.findRates()).thenReturn(rates);
        //when
        Set<String> result = testSubject.findExchangeCurrenciesByTitle();
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        result.forEach(pare -> Assertions.assertEquals(expectedResult, pare));
        verify(currencyRepository, times(1)).findRates();
    }

    @Test
    void shouldConvertByTitleAndAmount() {
        //given
        String titleFrom = "USD";
        String titleTo = "RUB";
        Integer quantityFrom = 10;
        Currency currencyFrom = createCurrency(1L, "USD", null, 10);
        Currency currencyTo = createCurrency(2L, "RUB", null, 200);
        Rate rate = createRate(1L, 2D, RateType.SELLING_RATE, currencyFrom, currencyTo);
        when(currencyRepository.findRateByTitle(titleFrom, titleTo)).thenReturn(rate);
        Double expectedResult = 1000.0;
        //when
        Double result = testSubject.convertFromTo(titleFrom, quantityFrom, titleTo);
        //then
        Assertions.assertEquals(expectedResult, result);
        Assertions.assertNotNull(result);
        verify(currencyRepository, times(1)).findRateByTitle(titleFrom, titleTo);
    }

    @Test
    void shouldReturnExceptionWhenRateIsNull() {
        //given
        String titleFrom = "RUB";
        String titleTo = "EUR";
        Integer quantityFrom = 10;
        String errorMessage = "No such rate " + titleFrom + "/" + titleTo + " exist!";
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.convertFromTo(
                        titleFrom, quantityFrom, titleTo)
                );
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(currencyRepository, times(1)).findRateByTitle(titleFrom, titleTo);
    }

    @Test
    void shouldReturnExceptionWhenRateValueIsNull() {
        //given
        String titleFrom = "USD";
        String titleTo = "RUB";
        Integer quantityFrom = null;
        Currency currencyFrom = createCurrency(1L, "RUB", null, 10);
        Currency currencyTo = createCurrency(2L, "EUR", null, 200);
        Rate rate = createRate(1L, null, RateType.SELLING_RATE, currencyFrom, currencyTo);
        String errorMessage = "Such rate " + titleFrom + "/" + titleTo + " is Unavailable";
        when(currencyRepository.findRateByTitle(titleFrom, titleTo)).thenReturn(rate);
        //when
        WrongAmountException result = Assertions
                .assertThrows(WrongAmountException.class, () -> testSubject.convertFromTo(
                        titleFrom, quantityFrom, titleTo)
                );
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(currencyRepository, times(1)).findRateByTitle(titleFrom, titleTo);
    }
}
