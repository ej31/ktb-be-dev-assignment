package com.ktb.assignment.stock.controller;


import com.ktb.assignment.stock.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ktb.assignment.stock.dto.*;
import com.ktb.assignment.stock.security.ApiKeyValidator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;
    private final ApiKeyValidator apiKeyValidator;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();
    
    public StockController(StockService stockService, ApiKeyValidator apiKeyValidator) {
        this.stockService = stockService;
        this.apiKeyValidator = apiKeyValidator;
    }

    @GetMapping("/history")
    public ResponseEntity<String> getStockHistory(
            @RequestParam String companyCode,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestHeader(value = "x-api-key", required = false) String headerApiKey,
            @RequestParam(value = "apikey", required = false) String queryApiKey,
            @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        
        String apiKey = (headerApiKey != null) ? headerApiKey : queryApiKey;
        if (apiKey == null) return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"API key is required\"}");
        if (!apiKeyValidator.isValidApiKey(apiKey)) return ResponseEntity.status(403).body("{\"success\": false, \"message\": \"Invalid API key\"}");

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<StockHistoryResponse> history = stockService.getStockHistory(companyCode, start, end);

        ResponseWrapper<List<StockHistoryResponse>> responseWrapper =
                new ResponseWrapper<List<StockHistoryResponse>>(true, history, "Stock history retrieved successfully.");

        try {
            if ("xml".equalsIgnoreCase(format)) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_XML)
                        .body(xmlMapper.writeValueAsString(responseWrapper));
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonMapper.writeValueAsString(responseWrapper));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"success\": false, \"message\": \"Error generating response\"}");
        }
    }
}