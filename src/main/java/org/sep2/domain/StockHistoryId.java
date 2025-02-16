package org.sep2.domain;


import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@EqualsAndHashCode
public class StockHistoryId implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;

    public StockHistoryId() {}

    public StockHistoryId(String companyCode, LocalDate tradeDate) {
        this.companyCode = companyCode;
        this.tradeDate = tradeDate;
    }
}