package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 주식 거래 이력(StocksHistory) 엔터티
 * - 특정 기업의 주식 거래 내역을 저장하는 테이블과 매핑
 * - 기업 코드(company_code) + 거래 날짜(trade_date)를 복합 키로 설정
 */
@Entity
@Table(name = "stocks_history") // 테이블 `stocks_history`와 매핑
@Getter
@NoArgsConstructor
@IdClass(StocksHistory.PK.class)  // PK 설정
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
    private float closingPrice;

    @Column(name = "volume", nullable = false)
    private float volume;

    /**
     * PK 클래스 (기업 코드 + 거래 날짜)
     * - `company_code`와 `trade_date`를 PK로 사용하기 위해 선언
     * - JPA의 @IdClass를 사용할 때 Serializable이 필수!
     */
    @Getter
    @NoArgsConstructor
    public static class PK implements Serializable {
        private String companyCode;
        private LocalDate tradeDate;
    }
}