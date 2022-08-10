package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Transaction;
import com.example.currencyconverter.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/transactions/")
@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody Transaction transaction){
        return new ResponseEntity<>(transactionService.create(transaction), HttpStatus.CREATED);
    }
}
