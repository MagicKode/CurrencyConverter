package com.example.currencyconverter.controller.exceptionHandler;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.exception.SuchElementIsExistException;
import com.example.currencyconverter.exception.WrongAmountException;
import com.example.currencyconverter.service.impl.CurrencyServiceImpl;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class.getName());

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> entity(NotFoundException notFoundException) {
        logger.log(Level.SEVERE, notFoundException.getMessage(), notFoundException);
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongAmountException.class)
    public ResponseEntity<String> entity(WrongAmountException wrongAmountException) {
        logger.log(Level.SEVERE, wrongAmountException.getMessage(), wrongAmountException);
        return new ResponseEntity<>(wrongAmountException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SuchElementIsExistException.class)
    public ResponseEntity<String> entity(SuchElementIsExistException suchElementIsExistException) {
        logger.log(Level.SEVERE, suchElementIsExistException.getMessage(), suchElementIsExistException);
        return new ResponseEntity<>(suchElementIsExistException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        String messages = methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }
}
