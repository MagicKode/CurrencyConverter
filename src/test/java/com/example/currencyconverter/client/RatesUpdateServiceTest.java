package com.example.currencyconverter.client;

import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatesUpdateServiceTest {
    @Mock
    RateExchangeClient rateExchangeClient;

    @Mock
    RateRepository rateRepository;

    @InjectMocks
    RatesUpdateService testSubject;

    @Test
    void shouldUpdateRatesByJSONWhenExchangeRateIn() {
        //given
        Currency currencyFrom = new Currency();
        Currency currencyTo = new Currency();
        currencyFrom.setTitle("USD");
        currencyTo.setTitle("BYN");
        Rate rateFromDB = new Rate();
        rateFromDB.setCurrencyFrom(currencyFrom);
        rateFromDB.setCurrencyTo(currencyTo);
        rateFromDB.setRateValue(1.23);

        Currency currencyJSONFrom = new Currency();
        Currency currencyJSONTo = new Currency();
        currencyJSONFrom.setTitle("USD");
        currencyJSONTo.setTitle("in");
        Rate rateFromJSON = new Rate();
        rateFromJSON.setCurrencyFrom(currencyJSONFrom);
        rateFromJSON.setCurrencyTo(currencyJSONTo);
        rateFromJSON.setRateValue(2.34);
        Map<String, Double> ratesFromJSON = new HashMap<>();
        ratesFromJSON.put(currencyJSONFrom + "_" + currencyJSONTo, 2.34);
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);

        //when
        testSubject.updateRatesByJSON();

        //then
        verify(rateRepository, times(1)).save(rateFromDB);
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateRatesByJSONWhenExchangeRateOut() {
        //given
        Currency currencyFrom = new Currency();
        Currency currencyTo = new Currency();
        currencyFrom.setTitle("USD");
        currencyTo.setTitle("BYN");
        Rate rateFromDB = new Rate();
        rateFromDB.setCurrencyFrom(currencyFrom);
        rateFromDB.setCurrencyTo(currencyTo);
        rateFromDB.setRateValue(1.23);

        Currency currencyJSONFrom = new Currency();
        Currency currencyJSONTo = new Currency();
        currencyJSONFrom.setTitle("USD");
        currencyJSONTo.setTitle("out");
        Rate rateFromJSON = new Rate();
        rateFromJSON.setCurrencyFrom(currencyJSONFrom);
        rateFromJSON.setCurrencyTo(currencyJSONTo);
        rateFromJSON.setRateValue(2.34);
        Map<String, String> ratesFromJSON = new HashMap<>();
        ratesFromJSON.put("USD_out","2.34");
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);

        //when
        testSubject.updateRatesByJSON();

        //then
        verify(rateRepository, times(1)).save(rateFromDB);
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateRatesByJSONWhenConversionRateIn() {
        //given
        Currency currencyFrom = new Currency();
        Currency currencyTo = new Currency();
        currencyFrom.setTitle("USD");
        currencyTo.setTitle("EUR");
        Rate rateFromDB = new Rate();
        rateFromDB.setCurrencyFrom(currencyFrom);
        rateFromDB.setCurrencyTo(currencyTo);
        rateFromDB.setRateValue(1.23);

        Currency currencyJSONFrom = new Currency();
        Currency currencyJSONTo = new Currency();
        currencyJSONFrom.setTitle("USD");
        currencyJSONTo.setTitle("EUR");
        Rate rateFromJSON = new Rate();
        rateFromJSON.setCurrencyFrom(currencyJSONFrom);
        rateFromJSON.setCurrencyTo(currencyJSONTo);
        rateFromJSON.setRateValue(2.34);
        Map<String, String> ratesFromJSON = new HashMap<>();
        ratesFromJSON.put("USD_EUR_in","2.34");
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);

        //when
        testSubject.updateRatesByJSON();

        //then
        verify(rateRepository, times(1)).save(rateFromDB);
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateRatesByJSONWhenConversionRateOut() {
        //given
        Currency currencyFrom = new Currency();
        Currency currencyTo = new Currency();
        currencyFrom.setTitle("USD");
        currencyTo.setTitle("out");
        Rate rateFromDB = new Rate();
        rateFromDB.setCurrencyFrom(currencyFrom);
        rateFromDB.setCurrencyTo(currencyTo);
        rateFromDB.setRateValue(1.23);

        Currency currencyJSONFrom = new Currency();
        Currency currencyJSONTo = new Currency();
        currencyJSONFrom.setTitle("EUR");
        currencyJSONTo.setTitle("USD");
        Rate rateFromJSON = new Rate();
        rateFromJSON.setCurrencyFrom(currencyJSONFrom);
        rateFromJSON.setCurrencyTo(currencyJSONTo);
        rateFromJSON.setRateValue(2.34);
        Map<String, String> ratesFromJSON = new HashMap<>();
        ratesFromJSON.put("USD_EUR_out","2.34");
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);

        //when
        testSubject.updateRatesByJSON();

        //then
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateRatesByJSONWhenExchangeDBRateIsNull() {
        //given
        Rate rateFromDB = null;

        Currency currencyJSONFrom = new Currency();
        Currency currencyJSONTo = new Currency();
        currencyJSONFrom.setTitle("EUR");
        currencyJSONTo.setTitle("USD");
        Rate rateFromJSON = new Rate();
        rateFromJSON.setCurrencyFrom(currencyJSONFrom);
        rateFromJSON.setCurrencyTo(currencyJSONTo);
        rateFromJSON.setRateValue(2.34);
        Map<String, String> ratesFromJSON = new HashMap<>();
        ratesFromJSON.put("USD_EUR_out","2.34");

        //when
        testSubject.updateRatesByJSON();

        //then
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateRatesByJSONWhenConversionDBRateNull() {
        //given
        Rate rateFromDB = null;

        Currency currencyJSONFrom = new Currency();
        Currency currencyJSONTo = new Currency();
        currencyJSONFrom.setTitle("EUR");
        currencyJSONTo.setTitle("USD");
        Rate rateFromJSON = new Rate();
        rateFromJSON.setCurrencyFrom(currencyJSONFrom);
        rateFromJSON.setCurrencyTo(currencyJSONTo);
        rateFromJSON.setRateValue(2.34);
        Map<String, String> ratesFromJSON = new HashMap<>();
        ratesFromJSON.put("USD_EUR_out","2.34");

        //when
        testSubject.updateRatesByJSON();

        //then
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldNotUpdateRatesByJSONWhenExchangeJSONRateIsNull() {
        //given
        Currency currencyFrom = new Currency();
        Currency currencyTo = new Currency();
        currencyFrom.setTitle("EUR");
        currencyTo.setTitle("USD");
        Rate rateFromDB = new Rate();
        rateFromDB.setCurrencyFrom(currencyFrom);
        rateFromDB.setCurrencyTo(currencyTo);
        rateFromDB.setRateValue(1.23);

        Rate rateFromJSON = null;
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);

        //when
        testSubject.updateRatesByJSON();

        //then
        verify(rateRepository, times(1)).save(rateFromDB);
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldNotUpdateRatesWhenConversionJSONRateIsNull() {
        //given
        Currency currencyFrom = new Currency();
        Currency currencyTo = new Currency();
        currencyFrom.setTitle("EUR");
        currencyTo.setTitle("USD");
        Rate rateFromDB = new Rate();
        rateFromDB.setCurrencyFrom(currencyFrom);
        rateFromDB.setCurrencyTo(currencyTo);
        rateFromDB.setRateValue(1.23);

        Rate rateFromJSON = null;
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);


        //when
        testSubject.updateRatesByJSON();

        //then
        verify(rateRepository, times(1)).save(rateFromDB);
        verify(rateRepository, times(1)).findAll();
    }
}
