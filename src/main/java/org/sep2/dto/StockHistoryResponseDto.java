package org.sep2.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StockHistoryResponseDto {
    private String companyName;
    private LocalDate tradeDate;
    private long tradePrice;

    public StockHistoryResponseDto(String companyName, LocalDate tradeDate, long tradePrice) {
        this.companyName = companyName;
        this.tradeDate = tradeDate;
        this.tradePrice = tradePrice;
    }

}
