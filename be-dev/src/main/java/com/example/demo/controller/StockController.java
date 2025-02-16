package com.example.demo.controller;

import com.example.demo.dto.StockResponseDTO;
import com.example.demo.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 주식 정보 조회 컨트롤러
 * - 특정 기업의 주식 종가 데이터를 조회하는 API 제공
 * - API 요청 시 API Key 인증이 필요 (Interceptor에서 처리됨)
 */
@RestController
@RequestMapping("/api/v1/stock")  // API v1
public class StockController {

    private final StockService stockService;

    /**
     * StockService 의존성 주입
     * @param stockService 주식 데이터를 조회하는 서비스
     */
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * 특정 기업의 주식 가격 조회 API
     * - 회사 코드, 조회 시작 날짜, 종료 날짜를 기준으로 데이터를 조회
     * - API Key 인증 필요
     *
     * @param companyCode 조회할 기업 코드 (예: "AAPL")
     * @param startDate 조회 시작 날짜 (yyyy-MM-dd)
     * @param endDate 조회 종료 날짜 (yyyy-MM-dd)
     * @return 주식 종가 데이터 리스트 또는 400 Bad Request
     */
    @GetMapping("/{companyCode}")
    public ResponseEntity<List<StockResponseDTO>> getStockPrices(
        @PathVariable String companyCode,
        @RequestParam String startDate,
        @RequestParam String endDate
    ) {
        // 날짜 형식 검증
        LocalDate start, end;
        try {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(endDate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request 반환
        }

        // 주식 데이터 조회 및 응답 반환
        List<StockResponseDTO> response = stockService.getStockPrices(companyCode, start, end);
        return ResponseEntity.ok(response);
    }
}