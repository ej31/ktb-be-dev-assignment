package com.example.stockapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "stocks_history")
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", insertable = false, updatable = false)
    private Company company;

    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "close_price", nullable = false)
    private Float closingPrice;
}

