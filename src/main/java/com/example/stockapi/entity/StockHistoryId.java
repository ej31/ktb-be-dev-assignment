package com.example.stockapi.entity;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class StockHistoryId implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;
}

