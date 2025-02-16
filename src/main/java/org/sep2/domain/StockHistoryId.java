package org.sep2.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@EqualsAndHashCode
public class StockHistoryId implements Serializable {
    @Column(name = "company_code", nullable = false)
    private String companyCode;
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    public StockHistoryId() {}

    public StockHistoryId(String companyCode, LocalDate tradeDate) {
        this.companyCode = companyCode;
        this.tradeDate = tradeDate;
    }

    // Getter 추가
    public String getCompanyCode() {
        return companyCode;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

}