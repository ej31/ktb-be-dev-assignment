package org.ktb.stock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stock.dto.StockRequestDto;
import org.ktb.stock.dto.StockResponseDto;
import org.ktb.stock.dto.StockSearchDto;
import org.ktb.stock.global.common.ApiResponse;
import org.ktb.stock.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockController {
    private final StockService stockService;

    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/api/v1/stock")
    public ApiResponse<List<StockResponseDto>> getStocks(
            @RequestBody StockRequestDto stockRequestDto,
            @RequestHeader(name = "x-api-key", required = false) String requestApiKey) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            StockSearchDto stockSearchDto = new StockSearchDto(
                    stockRequestDto.getCompanyCode(),
                    formatter.parse(stockRequestDto.getStartDate()),
                    formatter.parse(stockRequestDto.getEndDate())
            );

            List<StockResponseDto> result = stockService.getStocks(stockSearchDto);
            return ApiResponse.success(result);
        } catch (ParseException e) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }
    }

    // API KEY 검증 로직
    private void validateApiKey(String requestApiKey) {
        if(requestApiKey == null) {
            log.info("api 키가 누락되었습니다.");
        }

        if(!requestApiKey.equals(apiKey)) {
            log.info("api 키가 일치하지 않습니다.");
        }
    }

//    // 사용자 요청 검증 로직
//    private void validateRequestDto(StockRequestDto stockRequestDto) {
//
//    }
}
