package com.example.currencyconverter.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TransactionDto {
    private Long id;
    private Integer transactionSum;
    private Integer incrementOperation;
    private Integer decrementOperation;
}
