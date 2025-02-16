package com.example.stockApi.controller.stock;

import com.example.stockApi.dto.stock.StockHistoryResponse;
import com.example.stockApi.entity.StocksHistoryEntity;
import com.example.stockApi.exception.ApiException;
import com.example.stockApi.service.StockHistoryService;
import com.example.stockApi.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/stock/history")
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;
    @Autowired
    public StockHistoryController(StockHistoryService stockHistoryService){
        this.stockHistoryService = stockHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<StockHistoryResponse>> getStockHistory(
            @RequestParam String company_code,
            @RequestParam String start_date,
            @RequestParam String end_date,
            @RequestHeader(value = "x-api-key", required = false) String apiKey){

        validateApiKey(apiKey);
        if (start_date == null || start_date.isEmpty()) {
            throw new ApiException(ErrorCode.MISSING_START_DATE);
        }
        if (end_date == null || end_date.isEmpty()) {
            throw new ApiException(ErrorCode.MISSING_END_DATE);
        }

        LocalDate start = parseDate(start_date, "start");
        LocalDate end = parseDate(end_date, "end");

        return ResponseEntity.ok(stockHistoryService.getStockHistory(company_code, start, end));
    }

    private void validateApiKey(String apiKey){
        final String VALID_API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";
        if(apiKey == null || apiKey.isEmpty()){
            throw new ApiException(ErrorCode.MISSING_API_KEY);
        }
        if (!VALID_API_KEY.equals(apiKey)) {
            throw new ApiException(ErrorCode.INVALID_API_KEY);
        }
    }

    private LocalDate parseDate(String date, String type) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            if(type.equals("start")){
                throw new ApiException(ErrorCode.INVALID_START_DATE_FORMAT);
            } else {
                throw new ApiException(ErrorCode.INVALID_END_DATE_FORMAT);
            }
        }
    }
}
