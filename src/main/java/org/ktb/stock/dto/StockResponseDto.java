package org.ktb.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StockResponseDto {
    private String companyName;
    private LocalDate tradeDate;
    private float closingPrice;
}
