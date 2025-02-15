package com.ktb.yuni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@IdClass(StocksHistoryId.class)
@Table(name = "stocks_history")
public class StocksHistory {
    @Id
    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @Id
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "close_price", nullable = false)
    private Float closePrice;
}
