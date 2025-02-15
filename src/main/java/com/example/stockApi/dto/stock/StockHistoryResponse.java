package com.example.stockApi.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryResponse {
    private String companyName;   // 기업명
    private LocalDate tradeDate;  // 거래 날짜
    private double closingPrice;  // 종가
}
