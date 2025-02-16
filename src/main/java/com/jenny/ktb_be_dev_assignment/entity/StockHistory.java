package com.jenny.ktb_be_dev_assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "stocks_history")
@IdClass(StockHistory.class)
public class StockHistory {

    @Id
    @Column(name = "company_code", nullable = false, length = 10)
    private String companyCode;

    @Id
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "open_price", nullable = false)
    private Float openPrice;

    @Column(name = "high_price", nullable = false)
    private Float highPrice;

    @Column(name = "low_price", nullable = false)
    private Float lowPrice;

    @Column(name = "close_price", nullable = false)
    private Float closePrice;

    @Column(name = "volume", nullable = false)
    private Float volume;

    @ManyToOne
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", insertable = false, updatable = false)
    private Company company;

}
