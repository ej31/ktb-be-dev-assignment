package com.ktb.yuni.controller;

import com.ktb.yuni.config.Constants;
import com.ktb.yuni.dto.ApiResponse;
import com.ktb.yuni.dto.StockResponseDto;
import com.ktb.yuni.exception.InvalidApiKeyException;
import com.ktb.yuni.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;
    private final Constants constants;


    public StockController(StockService stockService, Constants constants) {
        this.stockService = stockService;
        this.constants = constants;
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

        validateApiKey(apiKey);

        StockResponseDto response = stockService.getStockPrices(
                companyCode, LocalDate.parse(startDate), LocalDate.parse(endDate)
        );
        return ResponseEntity.ok(ApiResponse.success("주식 데이터 조회 성공", response));
    }

    /**
     * 요청 API 키의 유효성을 검증
     * - 키가 없거나 잘못된 경우 InvalidApiKeyException 발생
     */
    private void validateApiKey(String apiKey) {
        if (apiKey == null) {
            throw new InvalidApiKeyException("API key가 누락되었습니다.");
        }
        if (!constants.getApiKey().equals(apiKey)) {
            throw new InvalidApiKeyException("올바르지 않은 API key 입니다.");
        }
    }
}
