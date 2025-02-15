package org.ktb.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class StockSearchDto {
    private String companyCode;
    private Date startDate;
    private Date endDate;
}
