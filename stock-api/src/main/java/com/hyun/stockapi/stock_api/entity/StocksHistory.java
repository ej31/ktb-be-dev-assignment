package com.hyun.stockapi.stock_api.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="StocksHistory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StocksHistoryId.class)
public class StocksHistory {

    @Id
    @Column(name = "company_code", length = 10, nullable = false)
    private String companyCode;

    @Id
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "open_price", nullable = false)
    private float openPrice;

    @Column(name = "high_price", nullable = false)
    private float highPrice;

    @Column(name = "low_price", nullable = false)
    private float lowPrice;

    @Column(name = "close_price", nullable = false)
    private float closePrice;

    @Column(name = "volume", nullable = false)
    private float volume;

}
