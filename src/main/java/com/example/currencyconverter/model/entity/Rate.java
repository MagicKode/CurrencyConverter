package com.example.currencyconverter.model.entity;

import com.example.currencyconverter.model.entity.enums.RateType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "rate")
public class Rate extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long id;
    @Column(name = "rate_value")
    private Integer rateValue;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rate_type")
    private RateType rateType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_currency_from")
    private Currency currencyFrom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_currency_to")
    private Currency currencyTo;
}
