package com.example.currencyconverter.model.dto;

import com.example.currencyconverter.model.entity.enums.RateType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RateDto {
    private Long id;
    private Double rateValue;
    private RateType rateType;
}
