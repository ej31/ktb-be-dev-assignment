package com.ktb.yuni.controller;

import com.ktb.yuni.dto.ApiResponse;
import com.ktb.yuni.dto.StockResponseDto;
import com.ktb.yuni.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;

    private static final String VALID_API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getStockPrices(
            @RequestParam String companyCode,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestHeader(value = "x-api-key", required = false) String apiKeyHeader,
            @RequestParam(value = "apiKey", required = false) String apiKeyParam
    ) {
        String apiKey = (apiKeyHeader != null) ? apiKeyHeader : apiKeyParam;

        if (!VALID_API_KEY.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.failure("invalid api key"));
        }

        StockResponseDto response = stockService.getStockPrices(
                companyCode, LocalDate.parse(startDate), LocalDate.parse(endDate)
        );
        return ResponseEntity.ok(ApiResponse.success("주식 데이터 조회 성공", response));
    }
}
