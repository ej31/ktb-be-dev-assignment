package org.ktb.ktbbedevassignment.presentation;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.ktb.ktbbedevassignment.application.ApiKeyValidator;
import org.ktb.ktbbedevassignment.application.StockService;
import org.ktb.ktbbedevassignment.config.MapperConfig;
import org.ktb.ktbbedevassignment.dto.StockInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
@Import(MapperConfig.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
                        .andExpect(jsonPath("$.message").value("companyCode는 필수입니다."));
            }

            @Test
            @DisplayName("companyCode가 공백이면 400 Bad Request를 반환한다.")
            void getStocks_BlankCompanyCode_Returns400() throws Exception {
                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", " ")
                                .param("startDate", TEST_TRADE_DATE)
                                .param("endDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("companyCode는 필수입니다."));
            }

            @Test
            @DisplayName("companyCode가 null이면 400 Bad Request를 반환한다.")
            void getStocks_NullCompanyCode_Returns400() throws Exception {
                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", (String) null)
                                .param("startDate", TEST_TRADE_DATE)
                                .param("endDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("companyCode는 필수입니다."));
            }

            @Test
            @DisplayName("companyCode가 10자리 초과이면 400 Bad Request를 반환한다.")
            void getStocks_LongCompanyCode_Returns400() throws Exception {
                // given
                String over10LengthCompanyCode = "A".repeat(11);

                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", over10LengthCompanyCode)
                                .param("startDate", TEST_TRADE_DATE)
                                .param("endDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("companyCode는 10자 이하여야 합니다."));
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
                        .andExpect(jsonPath("$.message").value("startDate는 필수입니다."));
            }

            @ParameterizedTest
            @MethodSource("org.ktb.ktbbedevassignment.fixture.StockTestFixture#provideInvalidDates")
            @DisplayName("startDate 가 정해진 형식이 아니라면 400 Bad Request를 반환한다.")
            void getStocks_InvalidStartDate_Returns400(String startDate) throws Exception {
                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", TEST_COMPANY_CODE)
                                .param("startDate", startDate)
                                .param("endDate", TEST_TRADE_DATE)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("startDate는 yyyy-MM-dd 형식이어야 합니다."));
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
                        .andExpect(jsonPath("$.message").value("endDate는 필수입니다."));
            }

            @ParameterizedTest
            @MethodSource("org.ktb.ktbbedevassignment.fixture.StockTestFixture#provideInvalidDates")
            @DisplayName("endDate 가 정해진 형식이 아니라면 400 Bad Request를 반환한다.")
            void getStocks_InvalidEndDate_Returns400(String endDate) throws Exception {
                mockMvc.perform(get(REQUEST_URL)
                                .param("companyCode", TEST_COMPANY_CODE)
                                .param("startDate", TEST_TRADE_DATE)
                                .param("endDate", endDate)
                                .header("x-api-key", TEST_API_KEY)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("endDate는 yyyy-MM-dd 형식이어야 합니다."));
            }
        }
    }
}
