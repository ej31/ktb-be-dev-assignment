package kcs.kcsdev.stock.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import kcs.kcsdev.global.exception.GlobalException;
import kcs.kcsdev.global.exception.type.ErrorCode;
import kcs.kcsdev.stock.dto.CompanyResponse;
import kcs.kcsdev.stock.entity.Company;
import kcs.kcsdev.stock.entity.StocksHistory;
import kcs.kcsdev.stock.repository.CompanyRepository;
import kcs.kcsdev.stock.repository.StockHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StockServiceImplTest {

      @Mock
      private CompanyRepository companyRepository;

      @Mock
      private StockHistoryRepository stockHistoryRepository;

      @InjectMocks
      private StockServiceImpl stockService;

      @BeforeEach
      void setUp() {
            MockitoAnnotations.openMocks(this);
      }

      @Test
      void getStockHistory_companyNotFound() {
            String companyCode = "AAPL";
            LocalDate startDate = LocalDate.of(2024, 1, 2);
            LocalDate endDate = LocalDate.of(2024, 1, 26);

            when(companyRepository.findByCompanyCode(companyCode)).thenReturn(Optional.empty());

            GlobalException exception = assertThrows(GlobalException.class, () -> {
                  stockService.getStockHistory(companyCode, startDate, endDate);
            });

            assertEquals(ErrorCode.COMPANY_NOT_FOUND, exception.getErrorCode());
      }

      @Test
      void getStockHistory_historyNotFound() {
            String companyCode = "AAPL";
            LocalDate startDate = LocalDate.of(2024, 1, 2);
            LocalDate endDate = LocalDate.of(2024, 1, 26);
            Company company = Company.builder().companyCode(companyCode).build();

            when(companyRepository.findByCompanyCode(companyCode)).thenReturn(Optional.of(company));
            when(stockHistoryRepository.findByCompanyCompanyCodeAndTradeDateBetween(companyCode,
                    startDate, endDate))
                    .thenReturn(Optional.empty());

            GlobalException exception = assertThrows(GlobalException.class, () -> {
                  stockService.getStockHistory(companyCode, startDate, endDate);
            });

            assertEquals(ErrorCode.HISTORY_NOT_FOUND, exception.getErrorCode());
      }

      @Test
      void getStockHistory_success() {
            String companyCode = "AAPL";
            LocalDate startDate = LocalDate.of(2024, 1, 2);
            LocalDate endDate = LocalDate.of(2024, 1, 26);
            Company company = Company.builder()
                    .companyCode(companyCode)
                    .companyName("Apple Inc.")
                    .build();

            StocksHistory history = StocksHistory.builder()
                    .tradeDate(startDate)
                    .closePrice(150L)
                    .build();

            when(companyRepository.findByCompanyCode(companyCode)).thenReturn(Optional.of(company));
            when(stockHistoryRepository.findByCompanyCompanyCodeAndTradeDateBetween(companyCode,
                    startDate, endDate))
                    .thenReturn(Optional.of(Collections.singletonList(history)));

            CompanyResponse response = stockService.getStockHistory(companyCode, startDate,
                    endDate);

            assertEquals("Apple Inc.", response.getCompanyName());
            assertEquals(1, response.getStocksHistories().size());
            assertEquals("2024-01-02", response.getStocksHistories().get(0).getTradeDate());
            assertEquals(150L, response.getStocksHistories().get(0).getClosePrice());
      }
}
