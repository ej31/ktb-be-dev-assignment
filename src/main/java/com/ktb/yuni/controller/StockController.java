package com.ktb.yuni.controller;

import com.ktb.yuni.dto.StockResponseDto;
import com.ktb.yuni.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;

    private static final String VALID_API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<?> getStockPrices(
            @RequestParam String companyCode,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestHeader(value = "x-api-key", required = false) String apiKeyHeader,
            @RequestParam(value = "apiKey", required = false) String apiKeyParam
    ) {
        String apiKey = (apiKeyHeader != null) ? apiKeyHeader : apiKeyParam;

        if (!VALID_API_KEY.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Invalid API Key"));
        }

        try {
             StockResponseDto response = stockService.getStockPrices(companyCode, LocalDate.parse(startDate), LocalDate.parse(endDate));
             return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
