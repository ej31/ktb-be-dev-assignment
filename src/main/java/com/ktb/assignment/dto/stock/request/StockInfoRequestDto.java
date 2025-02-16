package com.ktb.assignment.dto.stock.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Builder
public record StockInfoRequestDto(

        @NotNull
        String companyCode,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate // StocksHistory table's tradeDate column

) {

}
