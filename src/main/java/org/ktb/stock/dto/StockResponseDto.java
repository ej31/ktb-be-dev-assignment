package org.ktb.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class StockResponseDto {
    private String companyName;
    private Date tradeDate;
    private float closingPrice;
}
