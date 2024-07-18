package kcs.kcsdev.stock.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import kcs.kcsdev.stock.dto.CompanyResponse;
import kcs.kcsdev.stock.dto.StocksHistoryResponse;
import kcs.kcsdev.stock.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StockControllerIntegrationTest {

      @Autowired
      private MockMvc mockMvc;

      @MockBean
      private StockService stockService;

      @BeforeEach
      void setUp() {
            MockitoAnnotations.openMocks(this);

            String companyCode = "AAPL";
            LocalDate startDate = LocalDate.of(2024, 1, 2);
            LocalDate endDate = LocalDate.of(2024, 1, 26);

            // Mocked response
            CompanyResponse mockResponse = new CompanyResponse();
            mockResponse.setCompanyName("Apple Inc.");
            StocksHistoryResponse stocksHistoryResponse = StocksHistoryResponse.builder()
                    .tradeDate("2024-01-02")
                    .closePrice(186L)
                    .build();
            mockResponse.setStocksHistories(Collections.singletonList(stocksHistoryResponse));

            // Given
            given(stockService.getStockHistory(companyCode, startDate, endDate))
                    .willReturn(mockResponse);
      }

      @Test
      void getStocksHistory_success() throws Exception {
            mockMvc.perform(get("/api/v1/stock/history")
                            .param("companyCode", "AAPL")
                            .param("startDate", "2024-01-02")
                            .param("endDate", "2024-01-26")
                            .header("x-api-key", "c18aa07f-f005-4c2f-b6db-dff8294e6b5e"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.companyName").value("Apple Inc."))
                    .andExpect(jsonPath("$.stocksHistories[0].tradeDate").value("2024-01-02"))
                    .andExpect(jsonPath("$.stocksHistories[0].closePrice").value(186L));
      }

      @Test
      void getStocksHistory_missingApiKey() throws Exception {
            mockMvc.perform(get("/api/v1/stock/history")
                            .param("companyCode", "AAPL")
                            .param("startDate", "2024-01-02")
                            .param("endDate", "2024-01-26"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
      }

      @Test
      void getStocksHistory_invalidApiKey() throws Exception {
            mockMvc.perform(get("/api/v1/stock/history")
                            .param("companyCode", "AAPL")
                            .param("startDate", "2024-01-02")
                            .param("endDate", "2024-01-26")
                            .header("x-api-key", "invalid-api-key"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
      }
}
