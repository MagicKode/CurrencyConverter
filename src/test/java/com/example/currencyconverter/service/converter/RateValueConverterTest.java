package com.example.currencyconverter.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RateValueConverterTest {
    @InjectMocks
    RateValueConverter testSubject;

    @Test
    void shouldConvertToDatabaseColumn() {
        //given
        Double value = 234.56789;
        Integer expectedValue = 23457;
        //when
        Integer result = testSubject.convertToDatabaseColumn(value);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedValue, result);
    }

    @Test
    void shouldConvertToEntityAttribute() {
        //given
        Integer value = 23456784;
        Double expectedValue = 234567.84;
        //when
        Double result = testSubject.convertToEntityAttribute(value);
        //then
        Assertions.assertEquals(expectedValue, result);
    }
}
