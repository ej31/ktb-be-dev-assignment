package com.ktb.assignment.stock.controller;

import com.ktb.assignment.stock.exception.StockException;
import com.ktb.assignment.stock.security.ApiKeyValidator;
import com.ktb.assignment.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private StockService stockService;
    
    @Mock
    private ApiKeyValidator apiKeyValidator;
    
    @InjectMocks
    private StockController stockController;
    
    @Test
    void shouldReturn400WhenApiKeyIsMissing() {
        StockException exception = assertThrows(StockException.class, () -> 
            stockController.getStockHistory("AAPL", "2024-01-01", "2024-02-01", null, null, "json")
        );
        
        assertEquals(400, exception.getStatusCode());
        assertEquals("API key is required", exception.getMessage());
    }
    
    @Test
    void shouldReturn403WhenApiKeyIsInvalid() {
        when(apiKeyValidator.isValidApiKey("invalid-key")).thenReturn(false);
        
        StockException exception = assertThrows(StockException.class, () -> 
            stockController.getStockHistory("AAPL", "2024-01-01", "2024-02-01", "invalid-key", null, "json")
        );
        
        assertEquals(403, exception.getStatusCode());
        assertEquals("Invalid API key", exception.getMessage());
    }
    
    @Test
    void shouldReturnStockHistorySuccessfully() throws Exception {
        when(apiKeyValidator.isValidApiKey("valid-key")).thenReturn(true);
        when(stockService.getStockHistory(anyString(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());
        
        ResponseEntity<String> response = stockController.getStockHistory("AAPL", "2024-01-01", "2024-02-01", "valid-key", null, "json");
        
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Stock history retrieved successfully."));
    }
}
