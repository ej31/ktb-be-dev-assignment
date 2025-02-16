package com.hyun.stockapi.stock_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockResponseDto {
    private String companyName;
    private String tradeDate;
    private float closingPrice;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public StockResponseDto(String companyName, LocalDate tradeDate, Float closingPrice) {
        this.companyName = companyName;
        this.tradeDate = tradeDate.format(formatter);
        this.closingPrice = closingPrice;
    }
}
