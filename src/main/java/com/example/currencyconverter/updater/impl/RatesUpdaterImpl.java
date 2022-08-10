package com.example.currencyconverter.updater.impl;

import com.example.currencyconverter.client.RateExchangeClient;
import com.example.currencyconverter.updater.RatesUpdater;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RatesUpdaterImpl implements RatesUpdater {
    private final RateExchangeClient rateExchangeClient;
    private final RateRepository rateRepository;

    @Autowired
    public RatesUpdaterImpl(RateExchangeClient rateExchangeClient, RateRepository rateRepository) {
        this.rateExchangeClient = rateExchangeClient;
        this.rateRepository = rateRepository;
    }

    public void updateRatesByJSON() {
        Map<String, Double> ratesFromJson = rateExchangeClient.getRatesFromJson();
        rateRepository.findAll().forEach(rate -> {
            if (rate.getCurrencyTo().getTitle().equals("BYN")) {
                String exchangeTitleFromDB = rate.getCurrencyFrom().getTitle() + "_" + "in";
                setRateValue(rate, ratesFromJson, exchangeTitleFromDB);
            }
            else if (rate.getCurrencyFrom().getTitle().equals("BYN")) {
                String exchangeTitleFromDB = rate.getCurrencyTo().getTitle() + "_" + "out";
                setRateValue(rate, ratesFromJson, exchangeTitleFromDB);
            }
            else if (!rate.getCurrencyFrom().getTitle().equals("BYN") && !rate.getCurrencyTo().getTitle().equals("BYN")) {
                String conversionTitle = rate.getCurrencyFrom().getTitle() + "_" + rate.getCurrencyTo().getTitle() + "_" + "in";
                setRateValue(rate, ratesFromJson, conversionTitle);
            }
            if (!rate.getCurrencyFrom().getTitle().equals("BYN") && !rate.getCurrencyTo().getTitle().equals("BYN")) {
                String conversionTitle = rate.getCurrencyTo().getTitle() + "_" + rate.getCurrencyFrom().getTitle() + "_" + "out";
                setRateValue(rate, ratesFromJson, conversionTitle);
            }
            rateRepository.save(rate);
        });
    }

    private void setRateValue(Rate rate, Map<String, Double> jsonMap, String title) {
        Double rateFromJSON = jsonMap.get(title);
        if (rateFromJSON != null) {
            double rateValue = rateFromJSON;
            rate.setRateValue(rateValue);
        }
    }
}
