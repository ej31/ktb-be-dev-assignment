package org.ktb.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StockServiceDto {
    private String companyCode;
    private LocalDate startDate;
    private LocalDate endDate;
}
