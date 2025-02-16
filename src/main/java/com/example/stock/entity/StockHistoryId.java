package com.example.stock.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class StockHistoryId implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;

    public StockHistoryId() {

    }

    public StockHistoryId(String companyCode, LocalDate tradeDate) {
        this.companyCode = companyCode;
        this.tradeDate = tradeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockHistoryId that = (StockHistoryId) o;
        return Objects.equals(companyCode, that.companyCode) &&
                Objects.equals(tradeDate, that.tradeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, tradeDate);
    }
}
