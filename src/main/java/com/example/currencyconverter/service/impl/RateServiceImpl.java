package com.example.currencyconverter.service.impl;

import com.example.currencyconverter.exception.NotFoundException;
import com.example.currencyconverter.exception.SuchElementIsExistException;
import com.example.currencyconverter.exception.WrongAmountException;
import com.example.currencyconverter.mapper.RateMapper;
import com.example.currencyconverter.model.dto.MessageDto;
import com.example.currencyconverter.model.dto.RateDto;
import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import com.example.currencyconverter.repository.CurrencyRepository;
import com.example.currencyconverter.repository.RateRepository;
import com.example.currencyconverter.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final CurrencyRepository currencyRepository;
    private final RateMapper rateMapper;

    @Transactional
    @Override
    public RateDto create(Rate rate) {
        return rateMapper.toRateDto(rateRepository.save(rate));
    }

    @Transactional
    @Override
    public RateDto update(Rate rate) {
        Rate rateFromDb = rateRepository.findById(
                rate.getId()).orElseThrow(
                () -> new NotFoundException("No rate found with such id = " + rate.getId())
        );
        rateFromDb.setRateValue(rate.getRateValue());
        rateFromDb.setRateType(rate.getRateType());
        rateFromDb.setCurrencyFrom(rate.getCurrencyFrom());
        rateFromDb.setCurrencyTo(rate.getCurrencyTo());
        return rateMapper.toRateDto(rateRepository.save(rateFromDb));
    }

    @Override
    public List<RateDto> findAllRatesByTitle(String title) {
        List<Rate> byCurrencyTitle = rateRepository.findByCurrencyTitle(title);
        return rateMapper.toListRateDto(byCurrencyTitle);
    }

    @Override
    public List<RateDto> findBuyingSellingRatesByTitle(RateType rateType, String title) {
        return rateMapper.toListRateDto(rateRepository.findAllRatesByTitle(rateType, title));
    }

    @Override
    public List<RateDto> findConversionRatesByTitle(RateType rateType, String title) {
        return rateMapper.toListRateDto(rateRepository.findAllConversionRatesByTitle(rateType, title));
    }

    @Override
    public List<RateDto> findAllRates() {
        return rateMapper.toListRateDto(rateRepository.findAll());
    }

    @Transactional
    @Override
    public boolean updateListOfRates(List<Rate> rates) {
        rates.forEach(this::update);
        return true;
    }

    @Transactional
    @Override
    public MessageDto updateListOfRatesModify(List<Rate> rates) {
        Map<Long, Rate> rateMap = rates.stream().collect(Collectors.toMap(Rate::getId, Function.identity()));
        List<Long> idList = rates.stream().map(Rate::getId).collect(Collectors.toList());
        List<Rate> rateListFromDB = rateRepository.getListOfRatesByIds(idList);
        if (rateListFromDB.isEmpty()) {
            throw new NotFoundException("No rates with such ids found: " + idList);
        }
        List<Long> updatedRates = rateListFromDB.stream()
                .map(rateFromDB -> {
                    Rate rate = rateMap.get(rateFromDB.getId());
                    rateFromDB.setRateValue(rate.getRateValue());
                    return rateRepository.save(rateFromDB);
                })
                .map(Rate::getId)
                .collect(Collectors.toList());
        Set<Rate> notUpdated = rateMap.entrySet().stream()
                .filter(entry -> !updatedRates.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        MessageDto messageDto = new MessageDto("everything were updated");
        if (!notUpdated.isEmpty()) {
            String collect = notUpdated.stream()
                    .map(rate -> rate.getId().toString())
                    .collect(Collectors.joining(", "));
            messageDto.setMessage("Rates weren't updated: " + collect);
            return messageDto;
        }
        return messageDto;
    }

    @Override
    public RateDto createConversionRate(Rate newConversionRate) {
        Currency currencyFrom = currencyRepository
                .findById(newConversionRate.getCurrencyFrom().getId())
                .orElseThrow(() -> new NotFoundException(
                        "currencyFrom with id: " + newConversionRate.getCurrencyFrom().getId() + " not exist"
                ));
        Currency currencyTo = currencyRepository
                .findById(newConversionRate.getCurrencyTo().getId())
                .orElseThrow(() -> new NotFoundException(
                        "currencyTo with id: " + newConversionRate.getCurrencyTo().getId() + " not exist"
                ));
        Rate rateFromDb = rateRepository
                .findByTitleCurrencyFromAndTitleCurrencyTo(currencyFrom.getTitle(), currencyTo.getTitle());
        if (rateFromDb != null) {
            throw new SuchElementIsExistException("such rate is already exist");
        }
        String title = "BYN";
        if (title.equals(currencyFrom.getTitle()) || title.equals(currencyTo.getTitle())) {
            throw new WrongAmountException("'BYN' - not comparable type of currency");
        }
        return create(newConversionRate);
    }
}


//workFlow:
//1. Достаём все Rate из бд
//2. Проходимся по всему списку
//3. Условия:
// 3,1. Если Rate == null, то кидаем NotFoundException и message()
// 3.2. Если Rate.getCurrencyFrom == null || Rate.getCurrencyTo == null, то сообщение , что currency нет в бд
// 3,3. Если newRate.getCurrFrom.getTitle.equals 'BYN', то  сообщение , что c BYN нельзя создавать курс.




