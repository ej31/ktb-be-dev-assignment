package org.ktb.ktbbedevassignment.infrastructure;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.ktb.ktbbedevassignment.util.TestDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.ktb.ktbbedevassignment.infrastructure.CompanyTestFixture.TEST_COMPANY_CODE;
import static org.ktb.ktbbedevassignment.infrastructure.CompanyTestFixture.TEST_COMPANY_NAME;
import static org.ktb.ktbbedevassignment.infrastructure.CompanyTestFixture.TEST_OTHER_COMPANY_CODE;
import static org.ktb.ktbbedevassignment.infrastructure.CompanyTestFixture.TEST_OTHER_COMPANY_NAME;
import static org.ktb.ktbbedevassignment.infrastructure.StockTestFixture.TEST_CLOSING_PRICE;
import static org.ktb.ktbbedevassignment.infrastructure.StockTestFixture.TEST_TRADE_DATE;
import static org.ktb.ktbbedevassignment.infrastructure.StockTestFixture.plusDay;

@JdbcTest
@Import({StockRepository.class, TestDataHelper.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TestDataHelper testDataHelper;

    @BeforeEach
    void setup() {
        testDataHelper.clearAllTables();

        testDataHelper.insertCompany(TEST_COMPANY_CODE, TEST_COMPANY_NAME);
        testDataHelper.insertCompany(TEST_OTHER_COMPANY_CODE, TEST_OTHER_COMPANY_NAME);

        testDataHelper.insertStockHistory(TEST_COMPANY_CODE, TEST_TRADE_DATE);
        testDataHelper.insertStockHistory(TEST_COMPANY_CODE, plusDay(TEST_TRADE_DATE, 1));
        testDataHelper.insertStockHistory(TEST_OTHER_COMPANY_CODE, TEST_TRADE_DATE);
    }

    @Nested
    @DisplayName("기업 코드와 기간으로 주가 정보를 조회할 경우")
    class Describe_findStockInfoList {
        @Test
        @DisplayName("해당 기간에 주가 데이터가 있다면 반환한다.")
        void shouldReturnStockDataForGivenCompanyAndDateRange() {
            // given
            String companyCode = TEST_COMPANY_CODE;
            String startDate = TEST_TRADE_DATE;
            String endDate = plusDay(TEST_TRADE_DATE, 1);

            // when
            List<StockInfoDto> stocks = stockRepository.findStockInfoList(companyCode, startDate, endDate);

            // then
            assertThat(stocks).hasSize(2);
            assertThat(stocks.get(0).companyName()).isEqualTo(TEST_COMPANY_NAME);
            assertThat(stocks.get(0).tradeDate()).isBetween(startDate, endDate);
            assertThat(stocks.get(0).closingPrice()).isEqualTo(TEST_CLOSING_PRICE);
        }

        @Test
        @DisplayName("해당 기간에 주가 데이터가 없다면 빈 리스트를 반환한다.")
        void shouldReturnEmptyListWhenNoStockDataForGivenCompanyAndDateRange() {
            // given
            String companyCode = TEST_COMPANY_CODE;
            String startDate = plusDay(TEST_TRADE_DATE, 999);
            String endDate = plusDay(TEST_TRADE_DATE, 999);

            // when
            List<StockInfoDto> stocks = stockRepository.findStockInfoList(companyCode, startDate, endDate);

            // then
            assertThat(stocks).isEmpty();
        }

        @Test
        @DisplayName("해당 기업 코드에 해당하는 주가 데이터가 없다면 빈 리스트를 반환한다.")
        void shouldReturnEmptyListWhenNoStockDataForGivenCompanyCode() {
            // given
            testDataHelper.clearStockHistory();

            String companyCode = TEST_COMPANY_CODE;
            String startDate = TEST_TRADE_DATE;
            String endDate = plusDay(TEST_TRADE_DATE, 1);

            // when
            List<StockInfoDto> stocks = stockRepository.findStockInfoList(companyCode, startDate, endDate);

            // then
            assertThat(stocks).isEmpty();
        }
    }
}
