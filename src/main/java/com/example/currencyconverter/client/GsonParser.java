package com.example.currencyconverter.client;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
public class GsonParser {
    public RatesData[] parse(){
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("test.json");
            return gson.fromJson(reader, RatesData[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        GsonParser parser = new GsonParser();
        RatesData[] parse = parser.parse();
        Arrays.stream(parse).forEach(System.out::println);
    }
}
