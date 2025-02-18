package org.covy.ktbbedevassignment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

// 데이터 전송

@Getter
@Builder
public class StockResponseDto {
    private String companyName;
    private LocalDate tradeDate;
    private float closingPrice;
}

