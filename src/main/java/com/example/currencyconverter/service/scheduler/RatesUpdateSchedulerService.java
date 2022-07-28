package com.example.currencyconverter.service.scheduler;

import com.example.currencyconverter.model.dto.MessageDto;
import com.example.currencyconverter.model.entity.Rate;

import java.util.List;

public interface RatesUpdateSchedulerService {
    MessageDto updateListOfRatesFromSite(List<Rate> rates);
}
