package com.ktb.assignment.domain;



import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;



@EqualsAndHashCode
@NoArgsConstructor
public class StocksHistoryId implements Serializable {

    private String companyCode;
    private LocalDate tradeDate;

    public StocksHistoryId(String companyCode, LocalDate tradeDate) {
        this.companyCode = companyCode;
        this.tradeDate = tradeDate;
    }
}



