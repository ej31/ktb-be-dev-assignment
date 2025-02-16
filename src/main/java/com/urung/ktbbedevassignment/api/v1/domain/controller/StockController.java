package com.urung.ktbbedevassignment.api.v1.domain.controller;

import com.urung.ktbbedevassignment.api.v1.domain.dto.StockPriceDto;
import com.urung.ktbbedevassignment.api.v1.domain.service.StockService;
import com.urung.ktbbedevassignment.global.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/api/v1/stockPrice")
    public ResponseEntity<JsonResponse> getStockPrice(@RequestPart("req") StockPriceDto.StockPriceRequest request) {
        // API 키 유효성 검사

        List<StockPriceDto.StockPriceResponse> response = stockService.getStockPrices(request);

        return ResponseEntity.ok(new JsonResponse(true, 200, "get Stock Price", response));
    }

}
