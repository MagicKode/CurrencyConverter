package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.entity.Currency;
import com.example.currencyconverter.model.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    void deleteByTitle(@Param("title") String title);

    @Query(value = "select c from Currency c")
    List<Currency> findAll();

    @Query(value = "select r " +
            "from Rate r " +
            "join fetch r.currencyFrom " +
            "join fetch r.currencyTo " +
            "where r.rateValue " +
            "is not null")
    List<Rate> findRates();

    @Query(value = "select r " +
            "from Rate r " +
            "join fetch r.currencyTo " +
            "join fetch r.currencyFrom " +
            "where r.currencyFrom.title = :titleFrom " +
            "and r.currencyTo.title = :titleTo")
    Rate findRateByTitle(String titleFrom, String titleTo);
}
