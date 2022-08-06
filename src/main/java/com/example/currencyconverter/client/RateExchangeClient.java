package com.example.currencyconverter.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Component
public class RateExchangeClient {
    public Map<String, Double> getRatesFromJson() {
        String url = "https://belarusbank.by/api/kursExchange?city=%D0%93%D0%BE%D0%BC%D0%B5%D0%BB%D1%8C";
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<RatesData[]> responseEntity = restTemplate.getForEntity(new URI(url), RatesData[].class);
            RatesData[] body = responseEntity.getBody();
            if (body == null) {
                throw new RuntimeException("RatesData is empty");
            } else {
                Map<String, Double> ratesFromJson;
                ratesFromJson = body[0].getRatesFromJSON();
                return ratesFromJson;
            }
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
