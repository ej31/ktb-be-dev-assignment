package com.ktb.assignment.controller;

import com.ktb.assignment.dto.ResponseDto;
import com.ktb.assignment.dto.stock.request.StockInfoRequestDto;
import com.ktb.assignment.dto.stock.response.StockInfoResponseDto;
import com.ktb.assignment.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping("")
    public ResponseDto<List<StockInfoResponseDto>> getStockHistory(@RequestBody StockInfoRequestDto dto){
        return ResponseDto.ok(stockService.getStockHistory(dto));
    }
}
