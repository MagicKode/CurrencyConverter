package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.dto.AccountDto;
import com.example.currencyconverter.model.entity.Account;
import com.example.currencyconverter.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/accounts/")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody Account account){
        return new ResponseEntity<>(accountService.create(account), HttpStatus.CREATED);
    }
}
