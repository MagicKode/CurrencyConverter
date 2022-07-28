package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.dto.CurrencyDto;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RequestMapping(path = "/currencies/")
@RestController
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public ResponseEntity<CurrencyDto> create(@Valid @RequestBody Currency currency) {
        return new ResponseEntity<>(currencyService.create(currency), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{title}")
    public ResponseEntity<Void> deleteByTitle(@PathVariable String title) {
        currencyService.deleteByTitle(title);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CurrencyDto>> findAll() {
        return new ResponseEntity<>(currencyService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "exchange")
    public ResponseEntity<Set<String>> getAllExchangeCurrencies() {
        return new ResponseEntity<>(currencyService.findExchangeCurrenciesByTitle(), HttpStatus.OK);
    }

    @PostMapping(path = "convert")
    public ResponseEntity<Double> convert(
            @RequestParam String titleFrom,
            @RequestParam Integer amount,
            @RequestParam String titleTo
    ) {
        return new ResponseEntity<>(currencyService.convertFromTo(titleFrom, amount, titleTo), HttpStatus.OK);
    }
}
