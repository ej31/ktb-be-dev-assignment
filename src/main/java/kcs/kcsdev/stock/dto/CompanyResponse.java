package kcs.kcsdev.stock.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import kcs.kcsdev.stock.entity.Company;
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
@JacksonXmlRootElement(localName = "company")
public class CompanyResponse {

      private String companyName;
      private List<StocksHistoryResponse> stocksHistories;


      public static CompanyResponse fromEntity(Company company) {
            return CompanyResponse.builder()
                    .companyName(company.getCompanyName())
                    .stocksHistories(company.getStocksHistories().stream()
                            .map(StocksHistoryResponse::fromEntity).toList())
                    .build();
      }

      public static CompanyResponse fromEntityWithHistories(Company company
              , List<StocksHistoryResponse> stocksHistories) {
            return CompanyResponse.builder()
                    .companyName(company.getCompanyName())
                    .stocksHistories(stocksHistories)
                    .build();
      }
}
