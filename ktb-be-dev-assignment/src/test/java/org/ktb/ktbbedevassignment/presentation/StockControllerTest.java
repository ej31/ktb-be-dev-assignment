package org.ktb.ktbbedevassignment.presentation;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ktb.ktbbedevassignment.application.ApiKeyValidator;
import org.ktb.ktbbedevassignment.application.StockService;
import org.ktb.ktbbedevassignment.dto.StockInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.ktb.ktbbedevassignment.fixture.ApiKeyTestFixture.TEST_API_KEY;
import static org.ktb.ktbbedevassignment.fixture.CompanyTestFixture.TEST_COMPANY_CODE;
import static org.ktb.ktbbedevassignment.fixture.CompanyTestFixture.TEST_COMPANY_NAME;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.TEST_CLOSING_PRICE;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.TEST_TRADE_DATE;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.createTestStockInfoDtoList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
class StockControllerTest {

    private static final String REQUEST_URL = "/api/v1/stocks";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockService stockService;

    @MockitoBean
    private ApiKeyValidator apiKeyValidator;

    private int stockInfoListSize = 2;
    private List<StockInfoDto> stockInfoList = createTestStockInfoDtoList(stockInfoListSize);

    @BeforeEach
    void setUp() {
        reset(stockService, apiKeyValidator);
    }

    @Nested
    @DisplayName("getStocks() API 테스트")
    class GetStocksTest {

        @Nested
        @DisplayName("성공 케이스")
        class SuccessCases {

            @Test
            @DisplayName("정상적인 요청 시 200 OK를 반환한다")
            void getStocks_ValidRequest_Returns200() throws Exception {
                // given
                doNothing().when(apiKeyValidator).validateApiKey(TEST_API_KEY);
                when(stockService.getStockInfo(any(), any(), any()))
                        .thenReturn(stockInfoList);

                // when & then
                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", TEST_COMPANY_CODE)
                                .param("startDate", TEST_TRADE_DATE)
                                .param("endDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.status").value(200))
                        .andExpect(jsonPath("$.message").value("Success"))
                        .andExpect(jsonPath("$.data").isArray())
                        .andExpect(jsonPath("$.data[0].companyName").value(TEST_COMPANY_NAME))
                        .andExpect(jsonPath("$.data[0].tradeDate").value(TEST_TRADE_DATE))
                        .andExpect(jsonPath("$.data[0].closingPrice").value(TEST_CLOSING_PRICE));
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        class FailureCases {
            @Test
            @DisplayName("필수 파라미터(companyCode)가 없으면 400 Bad Request를 반환한다.")
            void getStocks_MissingCompanyCode_Returns400() throws Exception {
                mockMvc.perform(get(REQUEST_URL)
                                .param("startDate", TEST_TRADE_DATE)
                                .param("endDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("필수 요청 파라미터가 없습니다: companyCode"));
            }

            @Test
            @DisplayName("필수 파라미터(startDate)가 없으면 400 Bad Request를 반환한다.")
            void getStocks_MissingStartDate_Returns400() throws Exception {
                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", TEST_COMPANY_CODE)
                                .param("endDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("필수 요청 파라미터가 없습니다: startDate"));
            }

            @Test
            @DisplayName("필수 파라미터(endDate)가 없으면 400 Bad Request를 반환한다.")
            void getStocks_MissingEndDate_Returns400() throws Exception {
                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", TEST_COMPANY_CODE)
                                .param("startDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("필수 요청 파라미터가 없습니다: endDate"));
            }
        }
    }
}
