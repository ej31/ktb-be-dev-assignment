package kcs.kcsdev.stock.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import kcs.kcsdev.stock.entity.StocksHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "stocksHistory")
public class StocksHistoryResponse {

      private String tradeDate;
      private Long closePrice;

      public static StocksHistoryResponse fromEntity(StocksHistory stocksHistory) {
            return StocksHistoryResponse.builder()
                    .tradeDate(stocksHistory.getTradeDate().toString())
                    .closePrice(stocksHistory.getClosePrice())
                    .build();
      }

}
