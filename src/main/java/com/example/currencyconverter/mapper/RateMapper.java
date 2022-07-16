package com.example.currencyconverter.mapper;


import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Rate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = CurrencyMapper.class)
public interface RateMapper {
    RateDto toRateDto(Rate rate);
    List<RateDto> toListRateDto(List<Rate> rates);
}
