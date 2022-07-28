package com.example.currencyconverter.repository;

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

    @Query(value = "select r from Rate r where r.id in :idList")
    List<Rate> getListOfRatesByIds(List<Long> idList);

    @Query(value = "select r " +
            "from Rate r " +
            "where r.id = :id")
    Rate findRateById(Long id);

    @Query("select r from Rate r " +
            "where r.currencyFrom.title = :titleCurrencyFrom and r.currencyTo.title = :titleCurrencyTo")
    Rate findByTitleCurrencyFromAndTitleCurrencyTo(String titleCurrencyFrom, String titleCurrencyTo);
}
