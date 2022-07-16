package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import com.example.currencyconverter.model.entity.enums.RateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query(value = "select r " +
            "from Rate r " +
            "where r.currencyTo.title = :title or r.currencyFrom.title = :title")
    List<Rate> findByCurrencyTitle(String title);

    @Query(value = "select r " +
            "from Rate r " +
            "where r.rateType = :rateType " +
            "and r.currencyFrom.title = :title " +
            "or r.currencyTo.title = :title")
    List<Rate> findAllRatesByTitle(RateType rateType, String title);

    @Query(value = "select r " +
            "from Rate r " +
            "where r.rateType = :rateType " +
            "and r.currencyFrom.title = 'CONVERSION_RATE' " +
            "or r.currencyTo.title = :title")
    List<Rate> findAllConversionRatesByTitle(RateType rateType, String title);

    @Query(value = "select r " +
            "from Rate r " +
            "where r.currencyFrom = :titleFrom " +
            "and r.currencyTo = :titleTo")
    List<Rate> findListOfRatesByTitle(String titleFrom, String titleTo);
}
