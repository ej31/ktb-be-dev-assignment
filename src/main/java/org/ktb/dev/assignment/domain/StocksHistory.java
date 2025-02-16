package org.ktb.dev.assignment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private float volume;
}
