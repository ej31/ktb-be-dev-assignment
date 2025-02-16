package ktb.mason.be_assignment.domain.stock.infrastructure.entity;

import jakarta.persistence.*;
import ktb.mason.be_assignment.domain.stock.domain.StockHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "stocks_history")
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockHistoryEntity {

    @EmbeddedId
    StockHistoryPK pk;

    @Column(name = "open_price")
    Double openPrice;

    @Column(name = "high_price")
    Double highPrice;

    @Column(name = "low_price")
    Double lowPrice;

    @Column(name = "close_price")
    Double closePrice;

    @Column(name = "volume")
    Double volume;

    @Builder
    public StockHistoryEntity(String companyCode, Double openPrice, Double highPrice, Double lowPrice, Double closePrice, Double volume, LocalDate tradeDate) {
        this.pk = StockHistoryPK.builder()
                .companyCode(companyCode)
                .tradeDate(tradeDate)
                .build();
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }

    public static StockHistoryEntity from(StockHistory stockHistory) {
        return StockHistoryEntity.builder()
                .companyCode(stockHistory.getCompanyCode())
                .openPrice(stockHistory.getOpenPrice())
                .highPrice(stockHistory.getHighPrice())
                .lowPrice(stockHistory.getLowPrice())
                .closePrice(stockHistory.getClosePrice())
                .volume(stockHistory.getVolume())
                .tradeDate(stockHistory.getTradeDate())
                .build();
    }

    public StockHistory toModel() {
        return StockHistory.builder()
                .companyCode(this.pk.getCompanyCode())
                .openPrice(this.openPrice)
                .highPrice(this.highPrice)
                .lowPrice(this.lowPrice)
                .closePrice(this.closePrice)
                .volume(this.volume)
                .tradeDate(this.pk.getTradeDate())
                .build();
    }
}
