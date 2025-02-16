package com.example.stockapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StockResponseDTO {
    private String companyName;
    private LocalDate tradeDate;
    private Float openPrice;
    private Float highPrice;
    private Float lowPrice;
    private Float closingPrice;
    private Float volume;
}
