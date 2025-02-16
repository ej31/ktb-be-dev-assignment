package com.ktb.assignment.domain;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;


@Entity
@Table(name = "stocks_history")
@IdClass(StocksHistoryId.class)  // 복합 키 클래스 적용
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StocksHistory {

    @Id
    @Column(name = "company_code", length = 10, nullable = false)
    private String companyCode;

    @Id
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @ManyToOne
    @JoinColumn(name = "company_code", insertable = false, updatable = false)
    private Company company;

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
