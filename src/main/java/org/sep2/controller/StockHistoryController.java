package org.sep2.controller;

import jakarta.validation.Valid;
import org.sep2.dto.StockHistoryRequestDto;
import org.sep2.dto.StockHistoryResponseDto;
import org.sep2.exception.ApiKeyInvalidException;
import org.sep2.exception.ApiKeyMissingException;
import org.sep2.exception.ResourceNotFoundException;
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
            @Valid @ModelAttribute StockHistoryRequestDto requestDto,
            @RequestHeader(value = "x-api-key", required = false) String headerApiKey) {
// 데이터 조회
        List<StockHistoryResponseDto> stockHistoryList = stockHistoryService.getStockHistory(
                requestDto.getCompanyCode(),
                requestDto.getStartDate(),
                requestDto.getEndDate());
        // api 검증
        String key = (headerApiKey != null) ? headerApiKey : apikey;
        if (key == null || key.isBlank()) {
            throw new ApiKeyMissingException("API Key가 누락되었습니다.");
        }
        if (!apiKeyProvider.isValid(key)) {
            throw new ApiKeyInvalidException("유효하지 않은 API Key입니다.");
        }

        if (stockHistoryList.isEmpty()) {
            throw new ResourceNotFoundException("해당 기간 동안의 주식 데이터가 없습니다.");
        }

        return ResponseEntity.ok(stockHistoryList);
    }
}
