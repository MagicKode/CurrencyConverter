package com.example.currencyconverter.service.schedule;

import com.example.currencyconverter.updater.impl.RatesUpdaterImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RateUpdateScheduler {
    private final RatesUpdaterImpl ratesUpdaterImpl;

    public RateUpdateScheduler(RatesUpdaterImpl ratesUpdaterImpl) {
        this.ratesUpdaterImpl = ratesUpdaterImpl;
    }

    @Scheduled(cron = "${daily.update.time}")
    @Transactional
    public void updateRatesScheduler() {
        ratesUpdaterImpl.updateRatesByJSON();
    }
}
