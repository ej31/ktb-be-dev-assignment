package com.jenny.ktb_be_dev_assignment.controller;

import com.jenny.ktb_be_dev_assignment.dto.StockRequest;
import com.jenny.ktb_be_dev_assignment.dto.StockResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class StocksHistoryController {

    @GetMapping("/stocks-history")
    public StockResponse getStocksHistory(@RequestBody StockRequest request) {

        return new StockResponse(
                "조회 성공!",
                new StockResponse.Data("Apple Inc.", "yyyy-mm-dd", Math.round(66.744))
        );
    }
}
