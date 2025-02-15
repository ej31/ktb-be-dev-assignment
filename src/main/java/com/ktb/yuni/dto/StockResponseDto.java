package com.ktb.yuni.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.util.List;

@Getter
@AllArgsConstructor
public class StockResponseDto {
    private String companyName;
    private List<StockDetail> stocks;

    @Getter
    @AllArgsConstructor
    public static class StockDetail {
        private String tradeDate;
        private Float closingPrice;
    }
}
