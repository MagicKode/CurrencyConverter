package com.example.currencyconverter.validation;

import com.example.currencyconverter.model.entity.Rate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

@ExtendWith(MockitoExtension.class)
class RateValueValidatorTest {
    @Mock
    ConstraintValidatorContext context;

    @InjectMocks
    RateValueValidator testSubject;

    @Test
    void ShouldNotValidIfValueLessThanZero() {
        //given
        Double value = -1.01;
        Rate rate = new Rate();
        rate.setRateValue(value);
        boolean expectedResult = false;
        //when
        boolean result = testSubject.isValid(value, context);
        //then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void ShouldValidIfValueGreaterThanZero() {
        //given
        Double value = 1.01;
        Rate rate = new Rate();
        rate.setRateValue(value);
        boolean expectedResult = true;
        //when
        boolean result = testSubject.isValid(value, context);
        //then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void ShouldNotValidIfValueIsZero() {
        //given
        Double value = 0.0;
        Rate rate = new Rate();
        rate.setRateValue(value);
        boolean expectedResult = true;
        //when
        boolean result = testSubject.isValid(value, context);
        //then
        Assertions.assertEquals(expectedResult, result);
    }
}
