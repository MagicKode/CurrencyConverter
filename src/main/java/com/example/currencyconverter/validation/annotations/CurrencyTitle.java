package com.example.currencyconverter.validation.annotations;

import com.example.currencyconverter.validation.CurrencyTitleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CurrencyTitleValidator.class)
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface CurrencyTitle {
    String message() default "invalid letter format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
