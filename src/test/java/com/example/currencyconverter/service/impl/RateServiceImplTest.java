package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.exception.SuchElementIsExistException;
import com.example.currencyconverter.exception.WrongAmountException;
import com.example.currencyconverter.mapper.RateMapper;
import com.example.currencyconverter.model.dto.MessageDto;
import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.repository.CurrencyRepository;
import com.example.currencyconverter.repository.RateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.currencyconverter.model.entity.enums.RateType.CONVERSION_RATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {
    @Mock
    RateRepository rateRepository;

    @Mock
    CurrencyRepository currencyRepository;

    @Mock
    RateMapper rateMapper;

    @InjectMocks
    RateServiceImpl testSubject;

    @Test
    void shouldCreate() {
        //given
        Rate rate = new Rate();
        //when
        testSubject.create(rate);
        //then
        verify(rateRepository, times(1)).save(rate);
    }

    @Test
    void shouldUpdate() {
        //given
        Currency currencyFromDB = new Currency();
        currencyFromDB.setId(1L);
        currencyFromDB.setTitle("UAH");
        currencyFromDB.setMeaning("Ukrainian hryvnia");
        currencyFromDB.setQuantity(100);

        Currency currencyFromDB1 = new Currency();
        currencyFromDB.setId(1L);
        currencyFromDB.setTitle("USD");
        currencyFromDB.setMeaning("American dollar");
        currencyFromDB.setQuantity(50);

        Rate rateFromDB = new Rate();
        rateFromDB.setId(1L);
        rateFromDB.setRateValue(2.1);
        rateFromDB.setCurrencyTo(currencyFromDB);
        rateFromDB.setCurrencyFrom(currencyFromDB1);
        rateFromDB.setRateType(RateType.SELLING_RATE);

        RateDto rateDto = new RateDto();
        rateDto.setRateValue(rateFromDB.getRateValue());
        when(rateRepository.findById(rateFromDB.getId())).thenReturn(Optional.of(rateFromDB));
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);
        when(rateMapper.toRateDto(rateFromDB)).thenReturn(rateDto);
        //when
        RateDto result = testSubject.update(rateFromDB);
        //then
        assertEquals(rateFromDB.getRateValue(), result.getRateValue());
        verify(rateRepository, times(1)).findById(rateFromDB.getId());
        verify(rateRepository, times(1)).save(rateFromDB);
        verify(rateMapper, times(1)).toRateDto(rateFromDB);
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        Rate rate = new Rate();
        Long id = 1L;
        rate.setId(id);
        String errorMessage = "No rate found with such id = " + id;
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.update(rate));
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(rateRepository, times(1)).findById(id);
    }

    @Test
    void shouldFindAllRatesByTitle() {
        //given
        String title = "USD";
        List<Rate> rates = Collections.singletonList(new Rate());
        List<RateDto> rateDtos = Collections.singletonList(new RateDto());
        when(rateRepository.findByCurrencyTitle(title)).thenReturn(rates);
        when(rateMapper.toListRateDto(rates)).thenReturn(rateDtos);
        //when
        List<RateDto> result = testSubject.findAllRatesByTitle(title);
        //then
        assertEquals(1, result.size());
        assertEquals(rateDtos, result);
        verify(rateRepository, times(1)).findByCurrencyTitle(title);
        verify(rateMapper, times(1)).toListRateDto(rates);
    }

    @Test
    void shouldFindBuyingSellingRatesByTitle() {
        //given
        String title = "USD";
        RateType rateType = RateType.BUYING_RATE;
        List<Rate> rates = Collections.singletonList(new Rate());
        List<RateDto> rateDtos = Collections.singletonList(new RateDto());
        when(rateRepository.findAllRatesByTitle(rateType, title)).thenReturn(rates);
        when(rateMapper.toListRateDto(rates)).thenReturn(rateDtos);
        //when
        List<RateDto> result = testSubject.findBuyingSellingRatesByTitle(rateType, title);
        //then
        assertEquals(1, result.size());
        assertEquals(rateDtos, result);
        verify(rateRepository, times(1)).findAllRatesByTitle(rateType, title);
        verify(rateMapper, times(1)).toListRateDto(rates);
    }

    @Test
    void shouldFindConversionRatesByTitle() {
        //given
        String title = "USD";
        RateType rateType = CONVERSION_RATE;
        List<Rate> rates = Collections.singletonList(new Rate());
        List<RateDto> rateDtos = Collections.singletonList(new RateDto());
        when(rateRepository.findAllConversionRatesByTitle(rateType, title)).thenReturn(rates);
        when(rateMapper.toListRateDto(rates)).thenReturn(rateDtos);
        //when
        List<RateDto> result = testSubject.findConversionRatesByTitle(rateType, title);
        //then
        assertEquals(1, result.size());
        assertEquals(rateDtos, result);
        verify(rateRepository, times(1)).findAllConversionRatesByTitle(rateType, title);
        verify(rateMapper, times(1)).toListRateDto(rates);
    }

    @Test
    void shouldFindAllRates() {
        List<Rate> rates = Arrays.asList(new Rate(), new Rate(), new Rate());
        List<RateDto> rateDtos = Arrays.asList(new RateDto(), new RateDto(), new RateDto());
        when(rateRepository.findAll()).thenReturn(rates);
        when(rateMapper.toListRateDto(rates)).thenReturn(rateDtos);
        //when
        List<RateDto> result = testSubject.findAllRates();
        //then
        assertEquals(rateDtos, result);
        verify(rateRepository, times(1)).findAll();
        verify(rateMapper, times(1)).toListRateDto(rates);
    }

    @Test
    void shouldUpdateListOfRates() {
        //given
        MessageDto expectedResult = new MessageDto("everything were updated");
        Rate rateFromDB = new Rate();
        rateFromDB.setId(1L);
        rateFromDB.setRateValue(2.1);
        List<Rate> ratesFromDb = Collections.singletonList(rateFromDB);

        Rate rateUpdate = new Rate();
        rateUpdate.setId(1L);
        rateUpdate.setRateValue(5.1);
        List<Rate> ratesUpdate = Collections.singletonList(rateUpdate);
        List<Long> idList = ratesUpdate.stream().map(Rate::getId).collect(Collectors.toList());
        when(rateRepository.getListOfRatesByIds(idList)).thenReturn(ratesFromDb);
        when(rateRepository.save(rateFromDB)).thenReturn(rateFromDB);
        //when
        MessageDto result = testSubject.updateListOfRatesModify(ratesUpdate);
        //then
        assertEquals(result.getMessage(), expectedResult.getMessage());
        verify(rateRepository, times(1)).getListOfRatesByIds(idList);
        verify(rateRepository, times(1)).save(rateFromDB);
    }

    @Test
    void shouldUpdateListOfRatesButNotAllRates() {
        //given
        Rate rateFromDB2 = new Rate();
        rateFromDB2.setId(2L);
        rateFromDB2.setRateValue(3.1);
        List<Rate> ratesFromDb = Collections.singletonList(rateFromDB2);

        Rate rateUpdate1 = new Rate();
        rateUpdate1.setId(2L);
        rateUpdate1.setRateValue(5.1);
        Rate rateUpdate2 = new Rate();
        rateUpdate2.setId(3L);
        rateUpdate2.setRateValue(5.1);
        List<Rate> ratesUpdate = Arrays.asList(rateUpdate1, rateUpdate2);
        List<Long> idList = ratesUpdate.stream().map(Rate::getId).collect(Collectors.toList());
        when(rateRepository.getListOfRatesByIds(idList)).thenReturn(ratesFromDb);
        when(rateRepository.save(rateFromDB2)).thenReturn(rateFromDB2);
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage("Rates weren't updated: " + rateUpdate2.getId());

        //when
        MessageDto result = testSubject.updateListOfRatesModify(ratesUpdate);
        //then
        assertEquals(result.getMessage(), messageDto.getMessage());
        verify(rateRepository, times(1)).getListOfRatesByIds(idList);
        verify(rateRepository, times(1)).save(rateFromDB2);
    }

    @Test
    void shouldThrowExceptionWhenRatesNotFound() {
        //given
        Rate updateRate = new Rate();
        updateRate.setId(1L);
        updateRate.setRateValue(2.1);
        List<Rate> updatedRates = Collections.singletonList(updateRate);
        List<Rate> ratesFromDb = new ArrayList<>();
        List<Long> idList = updatedRates.stream().map(Rate::getId).collect(Collectors.toList());
        when(rateRepository.getListOfRatesByIds(idList)).thenReturn(ratesFromDb);
        String errorMessage = "No rates with such ids found: " + idList;
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.updateListOfRatesModify(updatedRates));
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(rateRepository, times(1)).getListOfRatesByIds(idList);
    }

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
    void shouldThrowExceptionCurrencyFromNotFound() {
        //given
        Currency currencyFrom = createCurrency(1L, "RUB");
        Rate newRate = createRate(2L, currencyFrom, null);
        String errorMessage = "currencyFrom with id: " + newRate.getCurrencyFrom().getId() + " not exist";
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.createConversionRate(newRate));
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(currencyRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionCurrencyToNotFound() {
        //given
        Currency currencyFrom = createCurrency(1L, "RUB");
        Currency currencyTo = createCurrency(2L, "EUR");
        Rate newRate = createRate(2L, currencyFrom, currencyTo);
        String errorMessage = "currencyTo with id: " + newRate.getCurrencyTo().getId() + " not exist";
        when(currencyRepository.findById(currencyFrom.getId())).thenReturn(Optional.of(currencyFrom));
        //when
        NotFoundException result = Assertions
                .assertThrows(NotFoundException.class, () -> testSubject.createConversionRate(newRate));
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(currencyRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionSuchRateExist() {
        //given
        Currency currencyFrom = createCurrency(1L, "RUB");
        Currency currencyTo = createCurrency(2L, "EUR");
        Rate newRate = createRate(2L, currencyFrom, currencyTo);
        String errorMessage = "such rate is already exist";
        when(currencyRepository.findById(currencyFrom.getId())).thenReturn(Optional.of(currencyFrom));
        when(currencyRepository.findById(currencyTo.getId())).thenReturn(Optional.of(currencyTo));
        when(rateRepository.findByTitleCurrencyFromAndTitleCurrencyTo(currencyFrom.getTitle(), currencyTo.getTitle()))
                .thenReturn(new Rate());
        //when
        SuchElementIsExistException result = Assertions
                .assertThrows(SuchElementIsExistException.class, () -> testSubject.createConversionRate(newRate));
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(currencyRepository, times(1)).findById(1L);
        verify(currencyRepository, times(1)).findById(2L);
        verify(rateRepository, times(1)).findByTitleCurrencyFromAndTitleCurrencyTo(
                currencyFrom.getTitle(), currencyTo.getTitle());
    }

    @Test
    void shouldThrowExceptionNotComparableCurrency() {
        //given
        Currency currencyFrom = createCurrency(1L, "BYN");
        Currency currencyTo = createCurrency(2L, "EUR");
        Rate newRate = createRate(2L, currencyFrom, currencyTo);
        String errorMessage = "'BYN' - not comparable type of currency";
        when(currencyRepository.findById(currencyFrom.getId())).thenReturn(Optional.of(currencyFrom));
        when(currencyRepository.findById(currencyTo.getId())).thenReturn(Optional.of(currencyTo));
        //when
        WrongAmountException result = Assertions
                .assertThrows(WrongAmountException.class, () -> testSubject.createConversionRate(newRate));
        //then
        Assertions.assertEquals(errorMessage, result.getMessage());
        verify(currencyRepository, times(1)).findById(1L);
        verify(currencyRepository, times(1)).findById(2L);
        verify(rateRepository, times(1)).findByTitleCurrencyFromAndTitleCurrencyTo(
                currencyFrom.getTitle(), currencyTo.getTitle());
    }

    @Test
    void shouldCreateConversionRate() {
        //given
        Currency currencyFrom = createCurrency(1L, "USD");
        Currency currencyTo = createCurrency(2L, "EUR");
        Rate newRate = createRate(2L, currencyFrom, currencyTo);
        RateDto rateDto = new RateDto();
        when(currencyRepository.findById(currencyFrom.getId())).thenReturn(Optional.of(currencyFrom));
        when(currencyRepository.findById(currencyTo.getId())).thenReturn(Optional.of(currencyTo));
        when(rateRepository.save(newRate)).thenReturn(newRate);
        when(rateMapper.toRateDto(newRate)).thenReturn(rateDto);
        //when
        RateDto result = testSubject.createConversionRate(newRate);
        //then
        Assertions.assertEquals(rateDto, result);
        verify(currencyRepository, times(1)).findById(1L);
        verify(currencyRepository, times(1)).findById(2L);
        verify(rateRepository, times(1)).findByTitleCurrencyFromAndTitleCurrencyTo(
                currencyFrom.getTitle(), currencyTo.getTitle());
        verify(rateRepository, times(1)).save(newRate);
        verify(rateMapper, times(1)).toRateDto(newRate);
    }
}
