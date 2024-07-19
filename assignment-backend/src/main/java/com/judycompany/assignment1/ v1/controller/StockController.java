package com.judycompany.assignment1.v1.controller;

import com.judycompany.assignment1.v1.model.StockHistory;
import com.judycompany.assignment1.v1.service.StockService;
import com.judycompany.assignment1.v1.service.QuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private QuotaService quotaService;

    private static final String API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    @GetMapping("/stocks/history")
    public ResponseEntity<?> getStockHistory(
            @RequestParam String companyCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String apikey,
            @RequestHeader(value = "x-api-key", required = false) String apiKeyHeader) {

        try {
            String apiKey = apikey != null ? apikey : apiKeyHeader;

            if (apiKey == null || !API_KEY.equals(apiKey)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            if (!quotaService.isAllowed(apiKey)) {
                return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
            }

            List<StockHistory> stockHistories = stockService.getStockHistory(companyCode, startDate, endDate);
            if (stockHistories.isEmpty()) {
                return new ResponseEntity<>("No stock history found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(stockHistories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔에 예외 로그 출력
            return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
