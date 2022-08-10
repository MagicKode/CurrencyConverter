package com.example.currencyconverter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RateExchangeClient {
    private final RestTemplate restTemplate = new RestTemplate();
    String url = "https://belarusbank.by/api/kursExchange?city=%D0%93%D0%BE%D0%BC%D0%B5%D0%BB%D1%8C";


    public static void main(String[] args) {
        String url = "https://belarusbank.by/api/kursExchange?city=%D0%93%D0%BE%D0%BC%D0%B5%D0%BB%D1%8C";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

//        try {
//            ResponseEntity<RatesData[]> responseEntity = restTemplate.getForEntity(new URI(url), RatesData[].class);
//            RatesData[] ratesData = responseEntity.getBody();
//            Arrays.stream(ratesData).forEach(System.out::println);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

    }
}
