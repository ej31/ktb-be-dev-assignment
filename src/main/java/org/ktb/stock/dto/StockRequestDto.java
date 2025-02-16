package org.ktb.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "주식 조회 요청 DTO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDto {
    @Schema(description = "회사 코드", example = "AAPL")
    @JsonProperty("company_code")
    private String companyCode;

    @Schema(description = "조회 시작 날짜", example = "2020-01-01")
    @JsonProperty("start_date")
    @Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])")
    private String startDate;

    @Schema(description = "조회 종료 날짜", example = "2020-01-31")
    @JsonProperty("end_date")
    @Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])")
    private String endDate;
}
