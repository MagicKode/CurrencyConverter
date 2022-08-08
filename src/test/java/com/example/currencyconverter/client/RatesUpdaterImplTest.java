package com.example.currencyconverter.client;

import com.example.currencyconverter.updater.impl.RatesUpdaterImpl;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatesUpdaterImplTest {

    @Mock
    RateExchangeClient rateExchangeClient;

    @Mock
    RateRepository rateRepository;

    @InjectMocks
    RatesUpdaterImpl testSubject;

    private Currency createCurrency(Long id, String title) {
        Currency currency = new Currency();
        currency.setId(id);
        currency.setTitle(title);
        return currency;
    }

    private Rate createRate(Long id, Currency currencyFrom, Currency currencyTo) {
        Rate rate = new Rate();
        rate.setId(id);
        rate.setCurrencyTo(currencyTo);
        rate.setCurrencyFrom(currencyFrom);
        return rate;
    }

    @Test
    void shouldUpdateRatesByJSONWhenExchangeRateIn() {
        //given
        Currency firstCurrencyFrom = createCurrency(1L, "USD");
        Currency firstCurrencyTo = createCurrency(4L, "BYN");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(2L, "EUR");
        Currency secondCurrencyTo = createCurrency(4L, "BYN");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("USD_in", 3.45D);
        mapJson.put("EUR_in", 4.56D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(3.45D, rateOneFromDB.getRateValue());
        assertEquals(4.56D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldUpdateRatesByJSONWhenExchangeRateOut() {
        //given
        Currency firstCurrencyFrom = createCurrency(1L, "BYN");
        Currency firstCurrencyTo = createCurrency(3L, "USD");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(2L, "BYN");
        Currency secondCurrencyTo = createCurrency(4L, "EUR");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("USD_out", 3.45D);
        mapJson.put("EUR_out", 4.56D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(3.45D, rateOneFromDB.getRateValue());
        assertEquals(4.56D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldUpdateRatesByJSONWhenConversionRateIn() {
        //given
        Currency firstCurrencyFrom = createCurrency(1L, "USD");
        Currency firstCurrencyTo = createCurrency(2L, "EUR");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(2L, "EUR");
        Currency secondCurrencyTo = createCurrency(4L, "RUB");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("USD_EUR_in", 3.45D);
        mapJson.put("EUR_RUB_in", 4.56D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(3.45D, rateOneFromDB.getRateValue());
        assertEquals(4.56D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldUpdateRatesByJSONWhenConversionRateOut() {
        //given
        Currency firstCurrencyFrom = createCurrency(2L, "EUR");
        Currency firstCurrencyTo = createCurrency(1L, "USD");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(4L, "RUB");
        Currency secondCurrencyTo = createCurrency(2L, "EUR");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("EUR_USD_out", 3.45D);
        mapJson.put("RUB_EUR_out", 4.56D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(3.45D, rateOneFromDB.getRateValue());
        assertEquals(4.56D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldUpdateRatesByJSONWhenOneOfExchangeDBRatesIsNull() {
        //given
        Currency firstCurrencyFrom = createCurrency(1L, "USD");
        Currency firstCurrencyTo = createCurrency(3L, "BYN");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(2L, "EUR");
        Currency secondCurrencyTo = createCurrency(3L, "BYN");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(5.67);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("USD_in", 2.34D);
        mapJson.put("EUR_in", 3.45D);
        mapJson.put("RUB_in", 4.56D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(2.34D, rateOneFromDB.getRateValue());
        assertEquals(3.45D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldUpdateRatesByJSONWhenOneOfConversionDBRatesIsNull() {
        //given
        Currency firstCurrencyFrom = createCurrency(1L, "USD");
        Currency firstCurrencyTo = createCurrency(4L, "RUB");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(2L, "EUR");
        Currency secondCurrencyTo = createCurrency(1L, "USD");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34D);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("USD_RUB_in", 3.45D);
        mapJson.put("EUR_USD_in", 4.56D);
        mapJson.put("RUB_USD_out", 5.67D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(3.45D, rateOneFromDB.getRateValue());
        assertEquals(4.56D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldUpdateDBRatesWhenOneOfJSONExchangeRatesIsNull() {
        //given
        Currency firstCurrencyFrom = createCurrency(2L, "EUR");
        Currency firstCurrencyTo = createCurrency(3L, "BYN");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(4L, "RUB");
        Currency secondCurrencyTo = createCurrency(2L, "BYN");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("RUB_in", 3.45D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(3.45D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldUpdateDBRatesWhenOneOfJSONConversionRatesIsNull() {
        //given
        Currency firstCurrencyFrom = createCurrency(2L, "EUR");
        Currency firstCurrencyTo = createCurrency(1L, "USD");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(4L, "RUB");
        Currency secondCurrencyTo = createCurrency(2L, "EUR");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("RUB_EUR_in", 3.45D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        assertEquals(3.45D, rateTwoFromDB.getRateValue());
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }

    @Test
    void shouldNotUpdateDBRatesWhenDBIsEmpty() {
        //given
        HashMap<String, Double> mapJson = new HashMap<>();
        mapJson.put("RUB_USD_out", 3.45D);
        when(rateExchangeClient.getRatesFromJson()).thenReturn(mapJson);
        //when
        testSubject.updateRatesByJSON();
        //then
        verify(rateExchangeClient, times(1)).getRatesFromJson();
        verify(rateRepository, times(1)).findAll();
    }

    @Test
    void shouldNotUpdateDBRatesWhenJSONIsEmpty() {
        //given
        Currency firstCurrencyFrom = createCurrency(2L, "EUR");
        Currency firstCurrencyTo = createCurrency(1L, "USD");
        Rate rateOneFromDB = createRate(1L, firstCurrencyFrom, firstCurrencyTo);
        rateOneFromDB.setRateValue(1.23);
        Currency secondCurrencyFrom = createCurrency(4L, "RUB");
        Currency secondCurrencyTo = createCurrency(2L, "EUR");
        Rate rateTwoFromDB = createRate(2L, secondCurrencyFrom, secondCurrencyTo);
        rateOneFromDB.setRateValue(2.34);
        List<Rate> ratesFromDB = Arrays.asList(rateOneFromDB, rateTwoFromDB);

        when(rateRepository.findAll()).thenReturn(ratesFromDB);
        when(rateRepository.save(rateOneFromDB)).thenReturn(rateOneFromDB);
        when(rateRepository.save(rateTwoFromDB)).thenReturn(rateTwoFromDB);
        //when
        testSubject.updateRatesByJSON();
        //then
        verify(rateRepository, times(1)).findAll();
        verify(rateRepository, times(1)).save(rateOneFromDB);
        verify(rateRepository, times(1)).save(rateTwoFromDB);
    }
}
