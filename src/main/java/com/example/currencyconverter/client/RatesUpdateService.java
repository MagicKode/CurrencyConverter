package com.example.currencyconverter.client;

import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RatesUpdateService {
    private final RateExchangeClient rateExchangeClient;
    private final RateRepository rateRepository;

    @Autowired
    public RatesUpdateService(RateExchangeClient rateExchangeClient, RateRepository rateRepository) {
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
            else if (!rate.getCurrencyFrom().getTitle().equals("BYN") && !rate.getCurrencyTo().getTitle().equals("BYN")) {
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


 /* mapFromJSON.forEach((key, value) -> {
            String[] s = key.split("_");
            String titleFrom;
            String titleTo;
            if (s.length == 2) {
                if ("in".equals(s[1])) {
                    titleFrom = s[0];
                    titleTo = "BYN";
                } else if ("out".equals(s[1])) {
                    titleFrom = "BYN";
                    titleTo = s[0];
                }
            } else if (s.length == 3) {
                if ("in".equals(s[2])) {
                    titleFrom = s[0];
                    titleTo = s[1];
                } else if ("out".equals(s[2])) {
                    titleFrom = s[1];
                    titleTo = s[0];
                }
            }
            RateRepository rateRepository = new RateRepository();
            Rate rate = rateRepository.findByTitleCurrencyFromAndTitleCurrencyTo(titleFrom, titleTo);
            if (rate != null) {
                rate.setRateValue(value);
                rateRepository.save(rate);
            }
            //get Rate from DB by titleFrom and titleTo
            //if Rate != null -> update RateValue
            //rate.setRateValue(value);
        });*/