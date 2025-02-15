package ktb.mason.be_assignment.domain.stock.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockInfo {
    final Company company;
    final List<StockHistory> stockHistories;
    @Builder
    public StockInfo(Company company, List<StockHistory> stockHistories) {
        this.company = company;
        this.stockHistories = stockHistories;
    }
}
