package com.example.currencyconverter.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long id;
    @Column(name = "currency_title")
    private String title;
    @Column(name = "currency_meaning")
    private String meaning;
    @Column(name = "currency_quantity")
    private Integer quantity;
}
