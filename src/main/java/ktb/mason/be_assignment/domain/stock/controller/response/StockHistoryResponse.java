package ktb.mason.be_assignment.domain.stock.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ktb.mason.be_assignment.domain.stock.domain.StockHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockHistoryResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    final LocalDate tradeDate;
    final Double closePrice;

    public static StockHistoryResponse from (StockHistory stockHistory) {
        return StockHistoryResponse.builder()
                .tradeDate(stockHistory.getTradeDate())
                .closePrice(stockHistory.getClosePrice())
                .build();
    }
}
