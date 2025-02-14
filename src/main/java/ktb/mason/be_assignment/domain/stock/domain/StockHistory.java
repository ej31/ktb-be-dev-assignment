package ktb.mason.be_assignment.domain.stock.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockHistory {
    final String companyCode;
    final Double openPrice;
    final Double highPrice;
    final Double lowPrice;
    final Double closePrice;
    final Double volume;
    final LocalDateTime tradeDate;

    @Builder
    public StockHistory(String companyCode, Double openPrice, Double highPrice, Double lowPrice, Double closePrice, Double volume, LocalDateTime tradeDate) {
        this.companyCode = companyCode;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.tradeDate = tradeDate;
    }
}
