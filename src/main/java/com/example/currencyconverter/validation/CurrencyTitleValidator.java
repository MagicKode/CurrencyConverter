package com.example.currencyconverter.validation;

import com.example.currencyconverter.validation.annotations.CurrencyTitle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyTitleValidator implements ConstraintValidator<CurrencyTitle, String> {
    @Override
    public boolean isValid(String currencyTitle, ConstraintValidatorContext context) {
        StringBuilder builder = new StringBuilder();
        Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher = regex.matcher(currencyTitle);
        if (currencyTitle.length() != 3) {
            builder.append("title should consist of 3 letters; ");
        }
        if (!currencyTitle.equals(currencyTitle.toUpperCase())) {
            builder.append("all letters should be UpperCase; ");
        }
        if (matcher.find() || currencyTitle.matches("[-+]?\\d+")) {
            builder.append("should not contain digits; ");
        }
        if (!matcher.find() && !currencyTitle.matches("[a-zA-Z]+")) {
            builder.append("should not be any special symbols; ");
        }
        if (builder.length() > 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(builder.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
