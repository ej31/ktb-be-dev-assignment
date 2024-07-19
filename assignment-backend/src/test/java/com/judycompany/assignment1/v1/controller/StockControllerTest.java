package com.judycompany.assignment1.v1.controller;

import com.judycompany.assignment1.v1.service.QuotaService;
import com.judycompany.assignment1.v1.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(com.judycompany.assignment1.v1.controller.StockController.class)
public class StockControllerTest {

    @MockBean
    private StockService stockService;

    @MockBean
    private QuotaService quotaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetStockHistoryWithoutApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/stocks/history")
                        .param("companyCode", "AAPL")
                        .param("startDate", "2022-01-01")
                        .param("endDate", "2022-12-31"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetStockHistoryWithInvalidApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/stocks/history")
                        .param("companyCode", "AAPL")
                        .param("startDate", "2022-01-01")
                        .param("endDate", "2022-12-31")
                        .param("apikey", "invalid-key"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetStockHistoryWithValidApiKey() throws Exception {
        when(quotaService.isAllowed("c18aa07f-f005-4c2f-b6db-dff8294e6b5e")).thenReturn(true);
        when(stockService.getStockHistory("AAPL", LocalDate.parse("2022-01-01"), LocalDate.parse("2022-12-31")))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/stocks/history")
                        .param("companyCode", "AAPL")
                        .param("startDate", "2022-01-01")
                        .param("endDate", "2022-12-31")
                        .param("apikey", "c18aa07f-f005-4c2f-b6db-dff8294e6b5e"))
                .andExpect(status().isNotFound());
    }
}
