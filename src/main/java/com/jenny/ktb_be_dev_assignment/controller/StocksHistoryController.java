package com.jenny.ktb_be_dev_assignment.controller;

import com.jenny.ktb_be_dev_assignment.dto.StockRequest;
import com.jenny.ktb_be_dev_assignment.dto.StockResponse;
import com.jenny.ktb_be_dev_assignment.service.StockHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StocksHistoryController {

    private final StockHistoryService stockHistoryService;

    public StocksHistoryController(StockHistoryService stockHistoryService){
        this.stockHistoryService = stockHistoryService;
    }

    @GetMapping("/stocks-history")
    public StockResponse getStocksHistory(@RequestBody StockRequest request) {
        List<StockResponse.Data> stockData = stockHistoryService.getStockHistory(
                request.getCompanyCode(),
                request.getStartDate(),
                request.getEndDate()
        );

        return new StockResponse("조회 성공!", stockData);
    }

}
