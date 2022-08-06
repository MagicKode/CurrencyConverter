package com.example.currencyconverter.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatesFromSiteDto {
    private String USD_in;
    private String USD_out;
    private String EUR_in;
    private String EUR_out;
    private String RUB_in;
    private String RUB_out;
    private String GBP_in;
    private String GBP_out;
    private String CAD_in;
    private String CAD_out;
    private String PLN_in;
    private String PLN_out;
    private String UAH_in;
    private String UAH_out;
    private String SEK_in;
    private String SEK_out;
    private String CHF_in;
    private String CHF_out;
    private String USD_EUR_in;
    private String USD_EUR_out;
    private String USD_RUB_in;
    private String USD_RUB_out;
    private String RUB_EUR_in;
    private String RUB_EUR_out;
    private String JPY_in;
    private String JPY_out;
    private String CNY_in;
    private String CNY_out;
    private String CZK_in;
    private String CZK_out;
    private String NOK_in;
    private String NOK_out;

    public RatesFromSiteDto(String USD_in, String USD_out, String EUR_in, String EUR_out, String RUB_in,
                            String RUB_out, String GBP_in, String GBP_out, String CAD_in, String CAD_out,
                            String PLN_in, String PLN_out, String UAH_in, String UAH_out, String SEK_in,
                            String SEK_out, String CHF_in, String CHF_out, String USD_EUR_in, String USD_EUR_out,
                            String USD_RUB_in, String USD_RUB_out, String RUB_EUR_in, String RUB_EUR_out,
                            String JPY_in, String JPY_out, String CNY_in, String CNY_out, String CZK_in,
                            String CZK_out, String NOK_in, String NOK_out) {
        this.USD_in = USD_in;
        this.USD_out = USD_out;
        this.EUR_in = EUR_in;
        this.EUR_out = EUR_out;
        this.RUB_in = RUB_in;
        this.RUB_out = RUB_out;
        this.GBP_in = GBP_in;
        this.GBP_out = GBP_out;
        this.CAD_in = CAD_in;
        this.CAD_out = CAD_out;
        this.PLN_in = PLN_in;
        this.PLN_out = PLN_out;
        this.UAH_in = UAH_in;
        this.UAH_out = UAH_out;
        this.SEK_in = SEK_in;
        this.SEK_out = SEK_out;
        this.CHF_in = CHF_in;
        this.CHF_out = CHF_out;
        this.USD_EUR_in = USD_EUR_in;
        this.USD_EUR_out = USD_EUR_out;
        this.USD_RUB_in = USD_RUB_in;
        this.USD_RUB_out = USD_RUB_out;
        this.RUB_EUR_in = RUB_EUR_in;
        this.RUB_EUR_out = RUB_EUR_out;
        this.JPY_in = JPY_in;
        this.JPY_out = JPY_out;
        this.CNY_in = CNY_in;
        this.CNY_out = CNY_out;
        this.CZK_in = CZK_in;
        this.CZK_out = CZK_out;
        this.NOK_in = NOK_in;
        this.NOK_out = NOK_out;
    }
}
