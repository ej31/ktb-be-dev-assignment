package com.example.stockapi.controller;

import com.example.stockapi.dto.StockResponseDTO;
import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    @Value("${api.key}")
    private String validApiKey;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<?> getStockData(
            @RequestParam(required = false) String companyCode,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader(value = "x-api-key", required = false) String headerApiKey,
            @RequestParam(value = "apikey", required = false) String queryApiKey
    ) {
        // API Key 검증
        if (headerApiKey == null && queryApiKey == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("API Key is required (either in header or query param)."));
        }

        String apiKey = (headerApiKey != null) ? headerApiKey : queryApiKey;
        if (!validApiKey.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(createErrorResponse("Invalid API Key."));
        }

        // 필수 파라미터 체크
        if (companyCode == null || companyCode.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("Missing required parameter: companyCode."));
        }
        if (startDate == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("Missing required parameter: startDate."));
        }
        if (endDate == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("Missing required parameter: endDate."));
        }

        // 주식 정보 조회
        List<StockResponseDTO> stocks = stockService.getStockData(companyCode, startDate, endDate);
        return ResponseEntity.ok(stocks);
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", message);
        return errorResponse;
    }
}
