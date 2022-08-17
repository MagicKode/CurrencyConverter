package com.example.currencyconverter.model.dto;

import com.example.currencyconverter.model.entity.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BankAccountDto {
    private Long id;
    private Currency currency;
    private Integer value;
}
