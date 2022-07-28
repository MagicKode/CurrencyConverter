package com.example.currencyconverter.validation;

import com.example.currencyconverter.validation.annotations.RateValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RateValueValidator implements ConstraintValidator<RateValue, Double> {

    @Override
    public boolean isValid(Double rateValue, ConstraintValidatorContext context) {
        return rateValue != null && rateValue >= 0.0;
    }
}
