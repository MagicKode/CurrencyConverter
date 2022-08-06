package com.example.currencyconverter.client;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateUpdateScheduler {
    private final RatesUpdateService ratesUpdateService;

    public RateUpdateScheduler(RatesUpdateService ratesUpdateService){
        this.ratesUpdateService = ratesUpdateService;
    }

    @Scheduled(cron = "${daily.update.time}")
//    @Scheduled(fixedDelayString = "${scheduler.interval}")
    @Transactional
    public void updateRatesScheduler() {
        ratesUpdateService.updateRatesByJSON();
    }
}
