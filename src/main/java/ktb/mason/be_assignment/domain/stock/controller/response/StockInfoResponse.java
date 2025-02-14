package ktb.mason.be_assignment.domain.stock.controller.response;

import ktb.mason.be_assignment.domain.stock.domain.StockInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockInfoResponse {
    final CompanyResponse company;
    final List<StockHistoryResponse> stocks;

    public static StockInfoResponse from (StockInfo stockInfo) {
        return StockInfoResponse.builder()
                .company(CompanyResponse.from(stockInfo.getCompany()))
                .stocks(stockInfo.getStocks().stream()
                        .map(StockHistoryResponse::from)
                        .toList())
                .build();
    }
}
