package com.example.currencyconverter.service.schedule;

import com.example.currencyconverter.factory.impl.RatesUpdateFactoryImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateUpdateScheduler {
    private final RatesUpdateFactoryImpl ratesUpdateFactoryImpl;

    public RateUpdateScheduler(RatesUpdateFactoryImpl ratesUpdateFactoryImpl){
        this.ratesUpdateFactoryImpl = ratesUpdateFactoryImpl;
    }

    @Scheduled(cron = "${daily.update.time}")
    @Transactional
    public void updateRatesScheduler() {
        ratesUpdateFactoryImpl.updateRatesByJSON();
    }
}
