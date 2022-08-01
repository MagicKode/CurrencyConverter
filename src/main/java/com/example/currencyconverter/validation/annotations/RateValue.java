package com.example.currencyconverter.validation.annotations;

import com.example.currencyconverter.validation.RateValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = RateValueValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface RateValue {
    String message() default "Invalid Rate Value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
