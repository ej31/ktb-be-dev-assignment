package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import lombok.Getter;

/**
 * 주식 조회 요청 DTO
 * - 사용자가 주식 데이터를 요청할 때 전달하는 데이터 객체
 * - 회사 코드, 시작 날짜, 종료 날짜를 포함
 * - 날짜 형식 검증 및 범위 유효성 검사 포함
 */
@Getter
public class StockRequestDTO {
    
    @NotBlank(message = "company_code는 필수 입력 값입니다.")
    private String companyCode;
    
    @NotBlank(message = "start_date는 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "start_date 형식이 올바르지 않습니다. (yyyy-MM-dd)")
    private String startDate;
    
    @NotBlank(message = "end_date는 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "end_date 형식이 올바르지 않습니다. (yyyy-MM-dd)")
    private String endDate;

    // 날짜 유효성 검사 로직 (start_date <= end_date)
    public boolean isValidDateRange() {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            return !start.isAfter(end); // startDate가 endDate보다 이후면 false 반환
        } catch (DateTimeParseException e) {
            return false; // 날짜 형식이 잘못되었을 경우 false 반환
        }
    }
}