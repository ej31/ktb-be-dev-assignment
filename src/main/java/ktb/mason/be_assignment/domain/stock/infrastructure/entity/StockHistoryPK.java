package ktb.mason.be_assignment.domain.stock.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@Getter
public class StockHistoryPK implements Serializable {
    @Column(name = "company_code")
    String companyCode;

    @Column(name = "trade_date")
    LocalDate tradeDate;

    @Builder
    public StockHistoryPK(String companyCode, LocalDate tradeDate) {
        this.companyCode = companyCode;
        this.tradeDate = tradeDate;
    }
}
