package com.urung.ktbbedevassignment.api.v1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class StockPriceDto {

    @Data
    @NoArgsConstructor
    public static class StockPriceRequest{
        private String companyCode;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        @Builder
        public StockPriceRequest(String companyCode, LocalDate startDate, LocalDate endDate) {
            this.companyCode = companyCode;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockPriceResponse{
        private String companyName;
        private String tradeDate;
        private float closingPrice;
    }
}
