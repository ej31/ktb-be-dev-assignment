package com.hyun.stockapi.stock_api.controller;


import com.hyun.stockapi.stock_api.dto.StockResponseDto;
import com.hyun.stockapi.stock_api.exception.ApiKeyInvalidException;
import com.hyun.stockapi.stock_api.exception.ApiKeyMissingException;
import com.hyun.stockapi.stock_api.service.StockService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private static final String VALID_API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StockResponseDto> getStocks(
            @RequestParam(required = false) String apikey,
            @RequestHeader(value ="x-api-key", required = false ) String headerApiKey,
            @RequestParam String companyCode,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate tradeDate
            ) {
        String key =(headerApiKey != null) ? headerApiKey : apikey;

        if(key ==null || key.isBlank()){
            throw new ApiKeyMissingException("API Key가 누락되었습니다. (쿼리파라미터 apikey 또는 헤더 x-api-key 사용)");
        }
        if (!VALID_API_KEY.equals(key)) {
            throw new ApiKeyInvalidException("유효하지 않은 API Key입니다.");
        }

        return stockService.getStockPrices(companyCode,tradeDate);


    }
}
