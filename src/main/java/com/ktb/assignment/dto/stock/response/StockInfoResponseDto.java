package com.ktb.assignment.dto.stock.response;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record StockInfoResponseDto(
        @NotNull
        String companyName,

        @NotNull
        String tradeDate,

        @NotNull
        float closingPrice
) {
    public static StockInfoResponseDto create(String companyName, String tradeDate, float closingPrice){
        return StockInfoResponseDto.builder()
                .companyName(companyName)
                .tradeDate(tradeDate)
                .closingPrice(closingPrice)
                .build();
    }
}
