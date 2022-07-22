/*
package com.example.currencyconverter.validation;

import com.example.currencyconverter.model.entity.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyTitleIdValidator implements ConstraintValidator<Currency, String> {

    private static final Logger log = LoggerFactory.getLogger(CurrencyTitleIdValidator.class);
    @Override
    public boolean isValid(Currency currency, ConstraintValidatorContext constraintValidatorContext) {
        if(!currency.getTitle().isEmpty() && ) {
            log.info("Received valid customerId of type 'UUID'");
            return true;
        } else {
            log.error("Received invalid customerId");
            return false;
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
*/
