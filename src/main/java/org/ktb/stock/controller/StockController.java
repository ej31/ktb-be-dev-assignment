package org.ktb.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stock.dto.StockRequestDto;
import org.ktb.stock.dto.StockResponseDto;
import org.ktb.stock.dto.StockSearchDto;
import org.ktb.stock.global.common.ApiResponse;
import org.ktb.stock.global.error.code.ErrorCode;
import org.ktb.stock.global.error.exception.BusinessException;
import org.ktb.stock.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockController {
    private final StockService stockService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("${api.key}")
    private String apiKey;

    /**
     * 주식 정보 조회
     * @param stockRequestDto 주식 조회 요청 DTO
     * @param requestApiKey API 키
     * @return 주식 정보 목록
     */
    @GetMapping("/api/v1/stocks")
    public ResponseEntity<ApiResponse<List<StockResponseDto>>> getStocks(
            @RequestBody StockRequestDto stockRequestDto,
            @RequestHeader(name = "x-api-key") String requestApiKey) {

        validateApiKey(requestApiKey);
        validateRequestDto(stockRequestDto);
        if(!stockService.getCompany(stockRequestDto.getCompanyCode())) throw new BusinessException(ErrorCode.INVALID_COMPANY_CODE);
        StockSearchDto stockSearchDto = convertToSearchDto(stockRequestDto);


        List<StockResponseDto> result = stockService.getStocks(stockSearchDto);
        return ApiResponse.success(result);
    }

    // API KEY 검증 로직
    private void validateApiKey(String requestApiKey) {
        if(!StringUtils.hasText(requestApiKey)) {
            log.info("api 키가 누락되었습니다.");
            throw new BusinessException(ErrorCode.MISSING_API_KEY);
        }

        if(!requestApiKey.equals(apiKey)) {
            log.info("api 키가 일치하지 않습니다.");
            throw new BusinessException(ErrorCode.INVALID_API_KEY);
        }
    }

    // 사용자 요청 검증 로직
    private void validateRequestDto(StockRequestDto stockRequestDto) {
        // 필수 입력 값이 없을 때
        if(!StringUtils.hasText(stockRequestDto.getCompanyCode())
        || !StringUtils.hasText(stockRequestDto.getStartDate())
        || !StringUtils.hasText(stockRequestDto.getEndDate())) {
            log.info("필수 입력 값이 누락되었습니다.");
            throw new BusinessException(ErrorCode.INVALID_REQUEST_PARAMETER);
        }

        // 날짜 형식 및 범위 검증
        try {
            LocalDate startDate = LocalDate.parse(stockRequestDto.getStartDate(), DATE_FORMATTER);
            LocalDate endDate = LocalDate.parse(stockRequestDto.getEndDate(), DATE_FORMATTER);

            if (startDate.isAfter(endDate)) {
                log.info("시작일자가 종료일자보다 늦습니다. startDate: {}, endDate: {}",
                        stockRequestDto.getStartDate(), stockRequestDto.getEndDate());
                throw new BusinessException(ErrorCode.INVALID_DATE_RANGE);
            }
        } catch (DateTimeParseException e) {
            log.info("날짜 형식이 올바르지 않습니다. startDate: {}, endDate: {}",
                    stockRequestDto.getStartDate(), stockRequestDto.getEndDate());
            throw new BusinessException(ErrorCode.INVALID_DATE_FORMAT);
        }
    }

    // StockRequestDto -> StockSearchDto
    private StockSearchDto convertToSearchDto(StockRequestDto requestDto) {
        return new StockSearchDto(
                requestDto.getCompanyCode(),
                LocalDate.parse(requestDto.getStartDate(), DATE_FORMATTER),
                LocalDate.parse(requestDto.getEndDate(), DATE_FORMATTER)
        );
    }
}
