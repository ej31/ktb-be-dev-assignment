package com.example.stockapi.controller;

import com.example.stockapi.dto.StockListResponseDTO;
import com.example.stockapi.dto.StockResponseDTO;
import com.example.stockapi.exception.BusinessException;
import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

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

    // JSON, XML 응답지원
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getStockData(
            @RequestParam String companyCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader(value = "x-api-key", required = false) String headerApiKey,
            @RequestParam(value = "apikey", required = false) String queryApiKey,
            @RequestParam(value = "format", required = false, defaultValue = "json") String format
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

        // XML 또는 JSON 응답 처리
        StockListResponseDTO response = new StockListResponseDTO(stocks);

        HttpHeaders headers = new HttpHeaders();
        if ("xml".equalsIgnoreCase(format)) {
            headers.setContentType(MediaType.APPLICATION_XML);
            return ResponseEntity.ok().headers(headers).body(response);
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity.ok().headers(headers).body(response);
        }
    }
}
