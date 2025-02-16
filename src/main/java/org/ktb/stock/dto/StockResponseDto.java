package org.ktb.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "주식 조회 응답 DTO")
@Getter
@AllArgsConstructor
public class StockResponseDto {
    @Schema(description = "회사명", example = "Apple Inc.")
    private String companyName;

    @Schema(description = "거래 날짜", example = "2020-01-15")
    private LocalDate tradeDate;

    @Schema(description = "종가", example = "72.7161")
    private float closingPrice;
}
