package org.example.stockapitest.controller;

import org.example.stockapitest.dto.StockDto;
import org.example.stockapitest.response.CommonResponse;
import org.example.stockapitest.service.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    private final StockServiceImpl stockServiceImpl;

    @Autowired
    public StockController(StockServiceImpl stockServiceImpl) {
        this.stockServiceImpl = stockServiceImpl;
    }

    @GetMapping
    public ResponseEntity<?> getStock(@RequestParam String companyCode,
                                      @RequestParam String startDate,
                                      @RequestParam String endDate) {
        List<StockDto> stocks = stockServiceImpl.getStockInfo(companyCode, startDate, endDate);
        return CommonResponse.createResponse(HttpStatus.OK, null, stocks);
    }
}
