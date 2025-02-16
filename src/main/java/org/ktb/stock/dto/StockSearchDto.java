package org.ktb.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StockSearchDto {
    private String companyCode;
    private LocalDate startDate;
    private LocalDate endDate;
}
