package com.example.currencyconverter.model.entity;

import com.example.currencyconverter.model.entity.enums.OperationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "bankTransaction")
@RequiredArgsConstructor
@Setter
@Getter
@Entity
public class Transaction extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "curr_value")
    private Integer transactionSum;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "operation")
    private OperationType operationType;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
}
