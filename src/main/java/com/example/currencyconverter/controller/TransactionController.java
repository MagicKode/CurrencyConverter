package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.dto.TransactionDto;
import com.example.currencyconverter.model.entity.Transaction;
import com.example.currencyconverter.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/transactions/")
@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "operation")
    public ResponseEntity<TransactionDto> operation(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.operations(transaction), HttpStatus.OK);
    }

    @PostMapping(path = "transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String titleFrom,
            @RequestParam String titleTo,
            @RequestParam Integer sum,
            @RequestParam(name = "account_id") Long accountId
    ){
        return new ResponseEntity<>(transactionService.currencyTransfer(titleFrom, titleTo, sum, accountId), HttpStatus.OK);
    }
}
