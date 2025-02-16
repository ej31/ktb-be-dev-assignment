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
@IdClass(StocksHistory.StockHistoryId.class)
public class StocksHistory {

    @Id
    private String companyCode;

    @Id
    private LocalDate tradeDate;

    @Column(nullable = false)
    private Float closePrice;


    @NoArgsConstructor
    @Getter
    @Setter
    public static class StockHistoryId implements Serializable {
        private String companyCode;
        private LocalDate tradeDate;
    }
}
