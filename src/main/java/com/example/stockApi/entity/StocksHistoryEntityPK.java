package com.example.stockApi.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode
@NoArgsConstructor
public class StocksHistoryEntityPK implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;
}
