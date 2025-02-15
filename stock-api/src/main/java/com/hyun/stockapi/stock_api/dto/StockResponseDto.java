package com.hyun.stockapi.stock_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockResponseDto {
    private String companyName;
    private String tradeDate;
    private float closingPrice;


}
