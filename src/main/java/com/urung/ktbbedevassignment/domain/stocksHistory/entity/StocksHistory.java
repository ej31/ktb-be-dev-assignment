package com.urung.ktbbedevassignment.domain.stocksHistory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Table(name = "stocks_history")
@NoArgsConstructor
@Entity
public class StocksHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "trade_date")
    private LocalDate tradeDate;

    @Column(name = "open_price")
    private float openPrice;

    @Column(name = "high_price")
    private float highPrice;

    @Column(name = "low_price")
    private float lowPrice;

    @Column(name = "close_price")
    private float closePrice;

    @Column(name = "volume")
    private float volume;
}
