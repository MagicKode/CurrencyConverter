package com.example.currencyconverter.controller.exceptionHandler;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.exception.WrongAmountException;
import com.example.currencyconverter.service.converterServiceImpl.impl.CurrencyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class.getName());

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> entity(
            NotFoundException notFoundException
    ) {
        logger.log(Level.SEVERE, notFoundException.getMessage(), notFoundException);
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WrongAmountException.class})
    public ResponseEntity<String> entity(
            WrongAmountException wrongAmountException
    ) {
        logger.log(Level.SEVERE, wrongAmountException.getMessage(), wrongAmountException);
        return new ResponseEntity<>(wrongAmountException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
