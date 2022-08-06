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

 /*   public String getRates() {
        try {
            TestDto[] response = restTemplate.getForObject(new URI(url), TestDto[].class);
            Arrays.stream(response).forEach(System.out::println);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}*/

    public static void main(String[] args) {
        String url = "https://belarusbank.by/api/kursExchange?city=%D0%93%D0%BE%D0%BC%D0%B5%D0%BB%D1%8C";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            ResponseEntity<RatesData[]> responseEntity = restTemplate.getForEntity(new URI(url), RatesData[].class);
            RatesData[] ratesData = responseEntity.getBody();
            Arrays.stream(ratesData).forEach(System.out::println);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        /*try {
            ResponseEntity<TestDto[]> responseEntity = restTemplate.getForEntity(new URI(url), TestDto[].class);
            TestDto[] dtos = responseEntity.getBody();
//            Arrays.stream(dtos).forEach(System.out::println);
             Arrays.stream(dtos)
                    .map(object -> mapper.convertValue(object, TestDto.class))
                    .map(TestDto::getEUR_in)
                    .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/

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
