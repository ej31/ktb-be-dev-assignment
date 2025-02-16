package org.ktb.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stock.dto.StockRequestDto;
import org.ktb.stock.dto.StockResponseDto;
import org.ktb.stock.dto.StockServiceDto;
import org.ktb.stock.global.common.CommonResponse;
import org.ktb.stock.global.error.code.ErrorCode;
import org.ktb.stock.global.error.exception.BusinessException;
import org.ktb.stock.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Tag(name = "주식 API", description = "주식 시세 정보를 조회해주는 API 입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class StockController {
    private final StockService stockService;

    @Value("${api.key}")
    private String apiKey;

    @Operation(
            summary = "주식 시세 정보 조회",
            description = "회사 코드와 기간(조회 시작 기간, 조회 종료 기간)으로 주식 시세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CommonResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CommonResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 파라미터",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CommonResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CommonResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "API 키 검증 실패",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CommonResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CommonResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "데이터가 존재하지 않음",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CommonResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CommonResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CommonResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CommonResponse.class))
                    }
            )
    })
    @PostMapping("/stocks")
    public ResponseEntity<CommonResponse<List<StockResponseDto>>> getStocks(
            @Valid @RequestBody StockRequestDto stockRequestDto,
            @RequestHeader(name = "x-api-key") String requestApiKey,
            @RequestHeader(name = "Accept", required = false) String acceptHeader) {

        validateApiKey(requestApiKey);
        validateRequestDto(stockRequestDto);
        if(!stockService.getCompany(stockRequestDto.getCompanyCode())) throw new BusinessException(ErrorCode.INVALID_COMPANY_CODE);

        MediaType mediaType = getValidMediaType(acceptHeader);

        StockServiceDto stockServiceDto = convertToServiceDto(stockRequestDto);
        List<StockResponseDto> result = stockService.getStocks(stockServiceDto);
        return CommonResponse.success(result, mediaType);
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
            LocalDate startDate = LocalDate.parse(stockRequestDto.getStartDate());
            LocalDate endDate = LocalDate.parse(stockRequestDto.getEndDate());

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

    // 미디어 타입 검증 로직
    private MediaType getValidMediaType(String acceptHeader) {
        // null 또는 */* 처리
        if (acceptHeader == null || acceptHeader.contains("*/*")) {
            return MediaType.APPLICATION_JSON;  // 기본값을 JSON으로 설정
        }

        // JSON 또는 XML만 지원
        if (acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return MediaType.APPLICATION_JSON;
        }
        if (acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            return MediaType.APPLICATION_XML;
        }

        // JSON, XML 외의 값이 들어오면 예외 처리
        throw new BusinessException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }


    // StockRequestDto -> StockServiceDto
    private StockServiceDto convertToServiceDto(StockRequestDto requestDto) {
        return new StockServiceDto(
                requestDto.getCompanyCode(),
                LocalDate.parse(requestDto.getStartDate()),
                LocalDate.parse(requestDto.getEndDate())
        );
    }
}
