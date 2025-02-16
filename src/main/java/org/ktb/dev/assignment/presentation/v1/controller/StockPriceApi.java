package org.ktb.dev.assignment.presentation.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import org.ktb.dev.assignment.core.response.SuccessResponse;
import org.ktb.dev.assignment.presentation.v1.dto.GetStockByCompanyCodeResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Tag(name = "주가 조회 API", description = "주가 정보를 조회")
@Validated
public interface StockPriceApi {

    @Operation(summary = "기업의 주가 조회 API", description = "기간 내 특정 기업의 주가 정보를 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 기업 주가 조회가 완료되었습니다.",
                    content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                    @Content(mediaType = MediaType.APPLICATION_XML_VALUE)
            })

    })
    @GetMapping(
            value="/api/v1/stocks/{companyCode}/prices",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    ResponseEntity<SuccessResponse<GetStockByCompanyCodeResponse>> getStockByCompany(
            @Parameter(description = "기업 코드 (최대 10자리)")
            @PathVariable("companyCode")
            @Size(max = 10, message = "기업코드는 10자리 이하여야 합니다")
            @Pattern(regexp = "^[A-Z0-9]+$", message = "기업코드는 영문 대문자와 숫자만 허용됩니다")
            String companyCode,

            @Parameter(description = "조회 시작 날짜 (yyyy-MM-dd)")
            @RequestParam("startDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @NotNull(message = "시작 날짜는 필수입니다")
            @Past(message = "시작 날짜는 과거 날짜여야 합니다")
            LocalDate startDate,

            @Parameter(description = "조회 종료 날짜 (yyyy-MM-dd)")
            @RequestParam("endDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @NotNull(message = "종료 날짜는 필수입니다")
            @PastOrPresent(message = "종료 날짜는 과거나 현재 날짜여야 합니다")
            LocalDate endDate
    );

}
