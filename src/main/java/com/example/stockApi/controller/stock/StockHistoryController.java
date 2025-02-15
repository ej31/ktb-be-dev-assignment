package com.example.stockApi.controller.stock;

import com.example.stockApi.dto.stock.StockHistoryResponse;
import com.example.stockApi.entity.StocksHistoryEntity;
import com.example.stockApi.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;
    @Autowired
    public StockHistoryController(StockHistoryService stockHistoryService){
        this.stockHistoryService = stockHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<StocksHistoryEntity>> getStockHistory(){
        String companyCode = "AMD";
        String startDate = "2020-09-22";
        String endDate = "2020-09-23";

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return ResponseEntity.ok(stockHistoryService.getStockHistory(companyCode, start, end));
    }
}
