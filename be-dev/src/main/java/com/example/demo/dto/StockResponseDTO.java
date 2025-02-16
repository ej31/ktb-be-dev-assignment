package com.example.demo.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * 주식 조회 응답 DTO
 * - API 응답에서 반환되는 주식 데이터 객체
 * - 특정 기업의 종가(closingPrice) 및 거래 날짜(tradeDate)를 포함
 */
@Getter
@AllArgsConstructor
public class StockResponseDTO {
    private String companyName;  // 응답 시 회사명 포함
    private String tradeDate;    // yyyy-MM-dd 형식 유지
    private long closingPrice;   // 종가 (tradePrice)
}