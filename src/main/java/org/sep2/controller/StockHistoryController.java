package org.sep2.controller;

import org.sep2.dto.StockHistoryResponseDto;
import org.sep2.service.StockHistoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.sep2.ApiKeyProvider;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockHistoryController {

    private final StockHistoryService stockHistoryService;
    private final ApiKeyProvider apiKeyProvider;

    @Value("${api.key}")  //
    private String configuredApiKey;

    public StockHistoryController(StockHistoryService stockHistoryService, ApiKeyProvider apiKeyProvider) {
        this.stockHistoryService = stockHistoryService;
        this.apiKeyProvider = apiKeyProvider;
    }

    @GetMapping
    public ResponseEntity<?> getStockHistory(
            @RequestParam String companyCode,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) String apikey,
            @RequestHeader(value = "x-api-key", required = false) String headerApiKey) {

        // 🔹 API Key 검증
        String key = (headerApiKey != null) ? headerApiKey : apikey;
        if (key == null || key.isBlank()) {
            return ResponseEntity.badRequest().body("API Key가 누락되었습니다.");
        }
        if (!apiKeyProvider.isValid(key)) {
            return ResponseEntity.status(403).body("잘못된 API Key입니다.");
        }

        // 🔹 서비스 호출하여 데이터 조회
        List<StockHistoryResponseDto> stockHistoryList = stockHistoryService.getStockHistory(companyCode, startDate, endDate);

        if (stockHistoryList.isEmpty()) {
            return ResponseEntity.status(404).body("해당 기간에 대한 주식 정보가 없습니다.");
        }

        return ResponseEntity.ok(stockHistoryList);
    }
}
