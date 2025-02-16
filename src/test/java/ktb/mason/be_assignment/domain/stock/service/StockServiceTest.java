package ktb.mason.be_assignment.domain.stock.service;

import ktb.mason.be_assignment.domain.stock.controller.response.StockInfoResponse;
import ktb.mason.be_assignment.domain.stock.domain.Company;
import ktb.mason.be_assignment.domain.stock.domain.StockHistory;
import ktb.mason.be_assignment.global.api.ApiException;
import ktb.mason.be_assignment.global.api.AppHttpStatus;
import ktb.mason.be_assignment.mock.TestContainer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StockServiceTest {

    /**
     * getStockInfoByCompanyCode
     */
    @Test
    void 기업의_종가_정보를_조회할_때_존재하지_않는_기업코드인_경우_조회할_수_없다() {
        // given
        TestContainer tc = TestContainer.builder().build();
        tc.fakeCompanyRepository.save(Company.builder()
                .companyCode("AAPL")
                .companyName("Apple Inc.")
                .build());

        // when
        ApiException result = assertThrows(
                ApiException.class,
                () -> tc.stockService.getStockInfoByCompanyCode(
                        "AMD",
                        "2020-01-02",
                        "2020-01-03"
                )
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.NOT_FOUND_COMPANY);
    }

    @Test
    void 기업의_종가_정보를_조회할_수_있다() {
        // given
        TestContainer tc = TestContainer.builder().build();
        tc.fakeCompanyRepository.save(Company.builder()
                .companyCode("AAPL")
                .companyName("Apple Inc.")
                .build());

        tc.fakeStockHistoryRepository.saveAll(List.of(
                StockHistory.builder()
                    .closePrice(72.7161)
                    .companyCode("AAPL")
                    .tradeDate(LocalDate.of(2020, 1, 2))
                    .build(),
                StockHistory.builder()
                    .closePrice(72.0091)
                    .companyCode("AAPL")
                    .tradeDate(LocalDate.of(2020, 1, 3))
                    .build()
        ));

        // when
        StockInfoResponse result =  tc.stockService.getStockInfoByCompanyCode(
                "AAPL",
                "2020-01-02",
                "2020-01-03"
        );

        // then
        assertAll(
                () -> assertThat(result.getCompany().getCode()).isEqualTo("AAPL"),
                () -> assertThat(result.getCompany().getName()).isEqualTo("Apple Inc."),
                () -> assertThat(result.getStockHistories().size()).isEqualTo(2),
                () -> assertThat(result.getStockHistories().get(0).getClosePrice()).isEqualTo(72.7161),
                () -> assertThat(result.getStockHistories().get(0).getTradeDate()).isEqualTo(LocalDate.of(2020, 1, 2))

        );
    }
}