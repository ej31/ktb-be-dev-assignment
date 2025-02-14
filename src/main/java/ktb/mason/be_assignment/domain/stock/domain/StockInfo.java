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
    final List<StockHistory> stocks;
    @Builder
    public StockInfo(Company company, List<StockHistory> stocks) {
        this.company = company;
        this.stocks = stocks;
    }
}
