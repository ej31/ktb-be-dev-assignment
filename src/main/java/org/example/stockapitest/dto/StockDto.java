package org.example.stockapitest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {
    private String company_name;
    private String trade_date;
    private long trade_price;
}
