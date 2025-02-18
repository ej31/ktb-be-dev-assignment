package org.covy.ktbbedevassignment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

// DB 테이블과 매핑

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stocks_history")
@IdClass(StockHistory.StockHistoryId.class) // 복합 키 설정
public class StockHistory {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = false)
    private Company company;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockHistoryId implements Serializable {
        private String company;
        private LocalDate tradeDate;
    }
}

