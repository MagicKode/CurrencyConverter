package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.dto.BankAccountDto;
import com.example.currencyconverter.model.entity.BankAccount;
import com.example.currencyconverter.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/bankAccounts/")
@RestController
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccountDto> create(@RequestBody BankAccount bankAccount){
        return new ResponseEntity<>(bankAccountService.create(bankAccount), HttpStatus.CREATED);
    }
}
