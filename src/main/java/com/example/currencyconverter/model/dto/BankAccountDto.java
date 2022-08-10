package com.example.currencyconverter.model.dto;

import com.example.currencyconverter.model.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BankAccountDto {
    private Long id;
    private Integer currencyId;
    private Integer currencyValue;
    private Account account;
}
