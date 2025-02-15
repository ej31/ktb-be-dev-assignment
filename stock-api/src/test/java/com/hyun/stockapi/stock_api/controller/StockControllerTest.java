package com.hyun.stockapi.stock_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.stockapi.stock_api.service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
class StockControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VALID_API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    @Test
    @DisplayName("1) API Key 누락시 400 Bad Request")
    void testMissingApiKey() throws Exception{

        mockMvc.perform(get("/api/v1/stocks")
                .param("companyCode","AAPL")
                .param("tradeDate","2020-01-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("API Key가 누락되었습니다. (쿼리파라미터 apikey 또는 헤더 x-api-key 사용)"));
    }

    @Test
    @DisplayName("2) 잘못된 API Key 403 Forbidden")
    void testInvalidApiKey() throws Exception{

        mockMvc.perform(get("/api/v1/stocks")
                .param("companyCode","AAPL")
                .param("tradeDate","2020-01-01")
                .param("apikey","invalid-key"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("유효하지 않은 API Key입니다."));
    }
    @Test
    @DisplayName("3) 필수 파라 미터 누락: companyCode 없음 400 Bad Request")
    void testMissingCompanyCode() throws Exception{

        mockMvc.perform(get("/api/v1/stocks")
                        .param("tradeDate", "2020-01-01")
                        .param("apikey", VALID_API_KEY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("companyCode 파라미터가 누락되었습니다."));
    }

    @Test
    @DisplayName("4) 날짜 형식이 올바르지 않은 경우 400 Bad Request")
    void testInvalidDateFormat() throws Exception{

        mockMvc.perform(get("/api/v1/stocks")
                .param("companyCode","AAPL")
                .param("tradeDate","202001-01")
                .param("apikey",VALID_API_KEY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 입력해주세요)"));
    }
    @Test
    @DisplayName("5) 정상 호출 => 200 OK")
    void testValidRequest() throws Exception {

        mockMvc.perform(get("/api/v1/stocks")
                        .param("companyCode", "AAPL")
                        .param("tradeDate", "2025-02-15")
                        .param("apikey", VALID_API_KEY))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


}
