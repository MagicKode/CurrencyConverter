package com.example.currencyconverter.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BankAccountDto {
    private Long id;
    private String currencyTitle;
    private Integer value;
}
