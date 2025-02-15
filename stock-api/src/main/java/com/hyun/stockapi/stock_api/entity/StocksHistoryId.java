package com.hyun.stockapi.stock_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StocksHistoryId implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;
}
