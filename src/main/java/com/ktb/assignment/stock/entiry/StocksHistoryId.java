package com.ktb.assignment.stock.entiry;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class StocksHistoryId implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;

    public StocksHistoryId() {}

    public StocksHistoryId(String companyCode, LocalDate tradeDate) {
        this.companyCode = companyCode;
        this.tradeDate = tradeDate;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StocksHistoryId that = (StocksHistoryId) o;
        return companyCode.equals(that.companyCode) && tradeDate.equals(that.tradeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, tradeDate);
    }
}