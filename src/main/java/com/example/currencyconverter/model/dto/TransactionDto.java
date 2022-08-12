package com.example.currencyconverter.model.dto;

import com.example.currencyconverter.model.entity.enums.OperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TransactionDto {
    private Long id;
    private Integer transactionSum;
    private OperationType operationType;
    private BankAccountDto bankAccountDto;
}
