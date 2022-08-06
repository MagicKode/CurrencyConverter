package com.example.currencyconverter.client;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class RatesData {
    private String filial_id;
    private String sap_id;
    private String info_worktime;
    private String street_type;
    private String street;
    private String filials_text;
    private String home_number;
    private String name;
    private String name_type;
    private Map<String, Double> ratesFromJSON = new HashMap<>();

    @JsonAnySetter
    public void setRatesFromJSON(String title, String value) {
        ratesFromJSON.put(title, Double.parseDouble(value));
    }
}
