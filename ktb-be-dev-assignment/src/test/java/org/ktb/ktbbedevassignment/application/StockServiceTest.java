package org.ktb.ktbbedevassignment.application;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ktb.ktbbedevassignment.exception.CompanyNotFoundException;
import org.ktb.ktbbedevassignment.infrastructure.CompanyRepository;
import org.ktb.ktbbedevassignment.infrastructure.StockInfoDto;
import org.ktb.ktbbedevassignment.infrastructure.StockRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.ktb.ktbbedevassignment.fixture.CompanyTestFixture.TEST_NOT_EXIST_COMPANY_CODE;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.createTestStockInfoDtoList;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.plusDay;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class StockServiceTest {

    private StockService stockService;
    private StockRepository stockRepository;
    private CompanyRepository companyRepository;

    private final int stockInfoDtoListSize = 2;
    private final List<StockInfoDto> stockInfoDtoList = createTestStockInfoDtoList(stockInfoDtoListSize);

    @BeforeEach
    void setUp() {
        stockRepository = mock(StockRepository.class);
        companyRepository = mock(CompanyRepository.class);
        stockService = new StockService(stockRepository, companyRepository);
    }

    @Nested
    @DisplayName("getStockInfo 테스트")
    class GetStockInfoTest {

        @Nested
        @DisplayName("성공 케이스")
        class SuccessCases {

            @Test
            @DisplayName("기업 코드가 존재하면 주가 정보를 조회한다")
            void getStockInfo_WhenCompanyExists_ReturnsStockList() {
                // given
                String companyCode = stockInfoDtoList.get(0).companyName();
                String startDate = stockInfoDtoList.get(0).tradeDate();
                String endDate = plusDay(startDate, 10);

                when(companyRepository.existsByCompanyCode(companyCode)).thenReturn(true);
                when(stockRepository.findStockInfoList(companyCode, startDate, endDate)).thenReturn(stockInfoDtoList);

                // when
                List<StockInfoDto> stocks = stockService.getStockInfo(companyCode, startDate, endDate);

                // then
                assertThat(stocks).hasSize(stockInfoDtoListSize);

                verify(companyRepository, times(1)).existsByCompanyCode(companyCode);
                verify(stockRepository, times(1)).findStockInfoList(companyCode, startDate, endDate);
                verifyNoMoreInteractions(companyRepository, stockRepository);
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        class FailureCases {

            @Test
            @DisplayName("존재하지 않는 기업 코드를 조회하면 예외가 발생한다")
            void getStockInfo_WhenCompanyDoesNotExist_ThrowsException() {
                // given
                String companyCode = TEST_NOT_EXIST_COMPANY_CODE;
                String startDate = stockInfoDtoList.get(0).tradeDate();
                String endDate = plusDay(startDate, 10);

                when(companyRepository.existsByCompanyCode(companyCode)).thenReturn(false);

                // when & then
                assertThrows(CompanyNotFoundException.class, () ->
                        stockService.getStockInfo(companyCode, startDate, endDate));

                verify(companyRepository, times(1)).existsByCompanyCode(companyCode);
                verifyNoMoreInteractions(companyRepository, stockRepository);
            }
        }
    }
}
