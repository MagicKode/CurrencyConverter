package com.example.currencyconverter.service.converter;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Component
@Converter
public class RateValueConverter implements AttributeConverter<Double, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Double attribute) {
        return (int) (Math.ceil(attribute * 100));
    }

    @Override
    public Double convertToEntityAttribute(Integer dbData) {
        return Double.valueOf(dbData) / 100.00;
    }
}
