package com.example.demo.controller;

import com.example.demo.service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc  // MockMvc 자동 설정
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;  // 실제 빈 대신 Mock 객체 사용

    private static final String API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

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
}