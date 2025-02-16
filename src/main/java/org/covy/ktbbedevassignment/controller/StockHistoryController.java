package org.covy.ktbbedevassignment.controller;

import lombok.RequiredArgsConstructor;
import org.covy.ktbbedevassignment.dto.StockResponseDto;
import org.covy.ktbbedevassignment.service.StockHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockHistoryController {

    private final StockHistoryService stockHistoryService;

    @GetMapping
    public ResponseEntity<List<StockResponseDto>> getStockHistory(
            @RequestParam String companyCode,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestHeader(value = "x-api-key", required = false) String apiKey,
            @RequestParam(value = "apikey", required = false) String apiKeyParam) {

        // API Key 검증
        String validApiKey = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";
        if ((apiKey == null && apiKeyParam == null) || (!validApiKey.equals(apiKey) && !validApiKey.equals(apiKeyParam))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // 서비스 호출
        List<StockResponseDto> stockData = stockHistoryService.getStockHistory(companyCode, startDate, endDate);

        return ResponseEntity.ok(stockData);
    }
}

