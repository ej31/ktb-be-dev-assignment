package com.ktb.assignment.controller;

import com.ktb.assignment.dto.ResponseDto;
import com.ktb.assignment.dto.stock.response.StockInfoResponseDto;
import com.ktb.assignment.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    @GetMapping("")
    public ResponseDto<List<StockInfoResponseDto>> getStockHistory(
            @RequestParam("company_code") String companyCode,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate){
        return ResponseDto.ok(stockService.getStockHistory(companyCode, startDate, endDate));
    }
}
