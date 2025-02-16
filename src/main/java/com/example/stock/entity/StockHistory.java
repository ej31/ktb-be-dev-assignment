package com.example.stock.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@IdClass(StockHistory.class)    // 복합 키 클래스
@Table(name = "stocks_history")
public class StockHistory {

    @Id
    @Column(name = "company_code", nullable = false)
    private String companyCode;     // 복합키 1

    @Id
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;    // 복합 키 2


    @ManyToOne
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = false)
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

/*
*
CREATE TABLE `stocks_history` (
  `company_code` varchar(10) NOT NULL,
  `trade_date` date NOT NULL,
  `open_price` float NOT NULL,
  `high_price` float NOT NULL,
  `low_price` float NOT NULL,
  `close_price` float NOT NULL,
  `volume` float NOT NULL,
  PRIMARY KEY (`company_code`,`trade_date`),
  CONSTRAINT `stocks_history_ibfk_1` FOREIGN KEY (`company_code`) REFERENCES `company` (`company_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
* */