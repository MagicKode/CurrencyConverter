package com.example.currencyconverter.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyTitleValidatorTest {
    ConstraintValidatorContext context;
    ConstraintValidatorContext.ConstraintViolationBuilder builder;
    CurrencyTitleValidator testSubject;

    @BeforeEach
    public void init() {
        context = Mockito.mock(ConstraintValidatorContext.class);
        builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        testSubject = new CurrencyTitleValidator();
    }

    @Test
    void shouldNOtValidWhenTitleConsistMoreThenThreeLetters() {
        //given
        String title = "EURO";
        boolean expectedResult = false;
        String message = "title should consist of 3 letters; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);

        //when
        boolean result = testSubject.isValid(title, context);

        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }

    @Test
    void shouldNOtValidWhenTitleConsistLessThenThreeLetters() {
        //given
        String title = "EU";
        boolean expectedResult = false;
        String message = "title should consist of 3 letters; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);

        //when
        boolean result = testSubject.isValid(title, context);
        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }

    @Test
    void shouldNotValidWhenTitleConsistOfNumbers() {
        //given
        String title = "1L3";
        boolean expectedResult = false;
        String message = "should not contain digits; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);
        //when
        boolean result = testSubject.isValid(title, context);
        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }

    @Test
    void shouldNOtValidWhenTitleIsInLowercase() {
        //given
        String title = "asd";
        boolean expectedResult = false;
        String message = "all letters should be UpperCase; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);
        //when
        boolean result = testSubject.isValid(title, context);
        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }

    @Test
    void shouldNotValidateWhenTitleConsistOfSpecialSymbols() {
        //given
        String title = "$&@";
        boolean expectedResult = false;
        String message = "should not be any special symbols; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);
        //when
        boolean result = testSubject.isValid(title, context);
        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }

    @Test
    void shouldNotValidateWhenTitleInLowerCaseAndConsistOfSpecialSymbolsAndDigits() {
        //given
        String title = "1$g";
        boolean expectedResult = false;
        String message = "all letters should be UpperCase; should not contain digits; should not be any special symbols; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);
        //when
        boolean result = testSubject.isValid(title, context);
        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }

    @Test
    void shouldNotValidateWhenTitleConsistOfSpecialSymbolsAndInLowerCase() {
        //given
        String title = "*&g";
        boolean expectedResult = false;
        String message = "all letters should be UpperCase; should not be any special symbols; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);
        //when
        boolean result = testSubject.isValid(title, context);
        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }

    @Test
    void shouldNotValidateWhenTitleInLowerCaseAndConsistOfSpecialSymbolsAndDigitsAndOutOfSize() {
        //given
        String title = "@1jK";
        boolean expectedResult = false;
        String message = "title should consist of 3 letters; " +
                "all letters should be UpperCase; " +
                "should not contain digits;" +
                " should not be any special symbols; ";
        when(context.buildConstraintViolationWithTemplate(message)).thenReturn(builder);
        //when
        boolean result = testSubject.isValid(title, context);
        //then
        assertEquals(expectedResult, result);
        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(message);
        verify(builder, times(1)).addConstraintViolation();
    }
}
