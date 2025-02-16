package com.example.stockapi.controller;

import com.example.stockapi.dto.StockResponseDTO;
import com.example.stockapi.exception.BusinessException;
import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
            @RequestParam String companyCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader(value = "x-api-key", required = false) String headerApiKey,
            @RequestParam(value = "apikey", required = false) String queryApiKey
    ) {
        // API Key 검증
        if (headerApiKey == null && queryApiKey == null) {
            throw new BusinessException("API Key is required (either in header or query param).", HttpStatus.BAD_REQUEST);
        }

        String apiKey = (headerApiKey != null) ? headerApiKey : queryApiKey;
        if (!validApiKey.equals(apiKey)) {
            throw new BusinessException("Invalid API Key.", HttpStatus.FORBIDDEN);
        }

        // 필수 파라미터 검증
        if (companyCode.isEmpty()) {
            throw new BusinessException("Missing required parameter: companyCode.", HttpStatus.BAD_REQUEST);
        }
        if (startDate == null || endDate == null) {
            throw new BusinessException("Missing required date parameters.", HttpStatus.BAD_REQUEST);
        }

        // 주식 정보 조회
        List<StockResponseDTO> stocks = stockService.getStockData(companyCode, startDate, endDate);
        return ResponseEntity.ok(stocks);
    }
}
