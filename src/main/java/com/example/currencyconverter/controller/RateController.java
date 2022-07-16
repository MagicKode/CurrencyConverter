package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.service.converterServiceImpl.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/rates/")
@RequiredArgsConstructor
@RestController
public class RateController {

    private final RateService rateService;

    @PostMapping
    public ResponseEntity<RateDto> create(@RequestBody Rate rate) {
        return new ResponseEntity<>(rateService.create(rate), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RateDto> update(@RequestBody Rate rate) {
        return new ResponseEntity<>(rateService.update(rate), HttpStatus.OK);
    }

    @GetMapping(path = "{title}")
    public ResponseEntity<List<RateDto>> getRatesByTitle(@PathVariable String title) {
        List<RateDto> dtos = rateService.findAllRatesByTitle(title);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(path = "{rateType}/{title}")
    public ResponseEntity<List<RateDto>> getBuyingSellingRatesByTitle(
            @PathVariable RateType rateType,
            @PathVariable String title
    ) {
        return new ResponseEntity<>(rateService.findBuyingSellingRatesByTitle(rateType, title), HttpStatus.OK);
    }

    @GetMapping(path = "conversion/{rateType}/{title}")
    public ResponseEntity<List<RateDto>> getConversionRatesByTitle(
            @PathVariable RateType rateType,
            @PathVariable String title
    ) {
        return new ResponseEntity<>(rateService.findConversionRatesByTitle(rateType, title), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RateDto>> findAllRates() {
        return new ResponseEntity<>(rateService.findAllRates(), HttpStatus.OK);
    }

    @PutMapping(path = "all/rates")
    public ResponseEntity<List<RateDto>> updateListOfRates(@RequestBody List<Rate> rates) {
        return new ResponseEntity<>(rateService.updateListOfRates(rates), HttpStatus.OK);
    }
}
