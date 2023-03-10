package com.example.currencyconverter.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CurrencyDto {
    private Long id;
    private String title;
    private String meaning;
    private Integer quantity;
}
