package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(StockHistory.StockHistoryId.class)
public class StockHistory {

    @Id
    private String companyCode;

    @Id
    private LocalDate tradeDate;

    @Column(nullable = false)
    private Float openPrice;

    @Column(nullable = false)
    private Float highPrice;

    @Column(nullable = false)
    private Float lowPrice;

    @Column(nullable = false)
    private Float closePrice;

    @Column(nullable = false)
    private Float volume;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class StockHistoryId implements Serializable {
        private String companyCode;
        private LocalDate tradeDate;
    }
}
