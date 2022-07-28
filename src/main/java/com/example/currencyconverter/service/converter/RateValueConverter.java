package com.example.currencyconverter.service.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
