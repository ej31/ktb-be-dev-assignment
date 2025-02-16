package com.example.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockResponseDto {
    private String companyName;
    private String tradeDate;
    private float closingPrice;
}