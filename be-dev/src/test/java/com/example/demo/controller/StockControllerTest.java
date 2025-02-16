package com.example.demo.controller;

import com.example.demo.service.StockService;
import com.example.demo.security.RateLimitInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc  // MockMvc 자동 설정
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;  // 실제 빈 대신 Mock 객체 사용

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    private static final String API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    @BeforeEach
    void resetRateLimit() {
        rateLimitInterceptor.resetRateLimit(); // 요청 기록 초기화
    }

    @Test
    @DisplayName("정상 요청 - 200 OK")
    void getStockPrices_Success() throws Exception {
        mockMvc.perform(get("/api/v1/stock/AAPL")
                .param("startDate", "2024-01-01")
                .param("endDate", "2024-01-10")
                .header("x-api-key", API_KEY))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("정상 요청 - JSON 응답")
    void getStockPrices_JSON_Success() throws Exception {
        mockMvc.perform(get("/api/v1/stock/AAPL")
                .param("startDate", "2024-01-01")
                .param("endDate", "2024-01-10")
                .header("x-api-key", API_KEY)
                .accept(MediaType.APPLICATION_JSON))  // JSON 응답 요청
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)); // JSON 타입 확인
    }

    @Test
    @DisplayName("정상 요청 - XML 응답")
    void getStockPrices_XML_Success() throws Exception {
        mockMvc.perform(get("/api/v1/stock/AAPL")
                .param("startDate", "2024-01-01")
                .param("endDate", "2024-01-10")
                .header("x-api-key", API_KEY)
                .accept(MediaType.APPLICATION_XML))  // XML 응답 요청
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML)); // XML 타입 확인
    }

    @Test
    @DisplayName("필수 파라미터 누락 - 400 Bad Request")
    void getStockPrices_MissingParams() throws Exception {
        mockMvc.perform(get("/api/v1/stock/AAPL")
                .header("x-api-key", API_KEY))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("API 키 누락 - 400 Bad Request")
    void getStockPrices_MissingApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/stock/AAPL")
                .param("startDate", "2024-01-01")
                .param("endDate", "2024-01-10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잘못된 API 키 - 403 Forbidden")
    void getStockPrices_InvalidApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/stock/AAPL")
                .param("startDate", "2024-01-01")
                .param("endDate", "2024-01-10")
                .header("x-api-key", "invalid-key"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Rate Limit 초과 시 429 Too Many Requests 반환")
    void getStockPrices_TooManyRequests() throws Exception {
        // 10초는 정상 응답이 반환됨
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(get("/api/v1/stock/AAPL")
                    .param("startDate", "2024-01-01")
                    .param("endDate", "2024-01-10")
                    .header("x-api-key", API_KEY))
                    .andExpect(status().isOk()); // 정상 응답
        }

        // 11번째 요청 → Rate Limit 초과 → 429 반환
        mockMvc.perform(get("/api/v1/stock/AAPL")
                .param("startDate", "2024-01-01")
                .param("endDate", "2024-01-10")
                .header("x-api-key", API_KEY))
                .andExpect(status().isTooManyRequests()); // 429 상태 코드
    }
}