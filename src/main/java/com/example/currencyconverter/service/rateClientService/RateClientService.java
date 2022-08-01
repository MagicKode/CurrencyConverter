package com.example.currencyconverter.service.rateClientService;

import com.example.currencyconverter.repository.RateRepository;
import com.example.currencyconverter.client.RateExchangeClient;
import org.springframework.stereotype.Service;

@Service
public class RateClientService {
    private final RateRepository rateRepository;
    private final RateExchangeClient rateExchangeClient;

    public RateClientService(RateRepository rateRepository, RateExchangeClient rateExchangeClient){
        this.rateRepository = rateRepository;
        this.rateExchangeClient = rateExchangeClient;
    }

//    public List<RatesFromSiteDto> findAll(){    // обращаемся к сайту, чтобы вернул список курсов
//        return rateExchangeClient.getRates().stream().map(rateDto -> );
//    }


}
