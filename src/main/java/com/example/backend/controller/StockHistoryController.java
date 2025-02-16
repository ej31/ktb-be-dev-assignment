package com.example.backend.controller;

import com.example.backend.entity.StocksHistory;
import com.example.backend.service.StockHistoryService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;
    private final Dotenv dotenv = Dotenv.load();
    // API Key 인증을 위한 상수
    String API_KEY = dotenv.get("API_KEY");

    @GetMapping
    public ResponseEntity<Object> getStockPrices(
            @RequestParam String companyCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader(value = "x-api-key", required = false) String headerApiKey,
            @RequestParam(value = "apikey", required = false) String queryApiKey
    ) {
        Map<String, String> errorResponse = new HashMap<>();

        if (companyCode == null || startDate == null || endDate == null) {
            errorResponse.put("error", "요청 파라미터 누락");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }

        if (headerApiKey == null && queryApiKey == null) {
            errorResponse.put("error", "API Key 누락");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
        String requestApiKey = (headerApiKey != null) ? headerApiKey : queryApiKey;
        if (!API_KEY.equals(requestApiKey)) {
            errorResponse.put("error", "잘못된 API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(errorResponse);
        }

        List<StocksHistory> stockPrices = stockHistoryService.getStockPrices(companyCode, startDate, endDate);
        return ResponseEntity.ok(stockPrices);
    }

}
