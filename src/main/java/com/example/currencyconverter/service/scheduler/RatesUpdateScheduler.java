package com.example.currencyconverter.service.scheduler;

import com.example.currencyconverter.model.dto.MessageDto;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.repository.RateRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatesUpdateScheduler implements RatesUpdateSchedulerService{
    private final RateRepository rateRepository;

    public RatesUpdateScheduler(RateRepository rateRepository){
        this.rateRepository = rateRepository;
    }
    @Override
    public MessageDto updateListOfRatesFromSite(List<Rate> rates) {
        return null;
    }
}
