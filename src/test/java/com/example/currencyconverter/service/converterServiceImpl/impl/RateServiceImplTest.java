package com.example.currencyconverter.service.converterServiceImpl.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.mapper.RateMapper;
import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.repository.RateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {
    @Mock
    RateRepository rateRepository;

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
        rateFromDB.setRateValue(2);
        rateFromDB.setCurrencyTo(currencyFromDB);  //пробовать new Currency и его параметры
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
        RateType rateType = RateType.CONVERSION_RATE;
        List<Rate> rates = Collections.singletonList(new Rate());
        List<RateDto> rateDtos = Collections.singletonList(new RateDto());
        when(rateRepository.findAllConversionRatesByTitle(rateType,title)).thenReturn(rates);
        when(rateMapper.toListRateDto(rates)).thenReturn(rateDtos);
        //when
        List<RateDto> result = testSubject.findConversionRatesByTitle(rateType,title);
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
}
