package com.ktb.assignment.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockHistoryResponse {
    private String companyName;
    private String tradeDate;
    private float closingPrice;
}