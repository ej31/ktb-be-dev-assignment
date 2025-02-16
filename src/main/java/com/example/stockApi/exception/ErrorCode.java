package com.example.stockApi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // API 관련 에러
    MISSING_API_KEY(HttpStatus.BAD_REQUEST.value(), "MISSING_API_KEY"),
    INVALID_API_KEY(HttpStatus.FORBIDDEN.value(), "INVALID_API_KEY"),

    // 파라미터 관련 에러
    MISSING_COMPANY_CODE(HttpStatus.BAD_REQUEST.value(), "MISSING_COMPANY_CODE"),
    MISSING_START_DATE(HttpStatus.BAD_REQUEST.value(), "MISSING_START_DATE"),
    MISSING_END_DATE(HttpStatus.BAD_REQUEST.value(), "MISSING_END_DATE"),

    INVALID_START_DATE_FORMAT(HttpStatus.BAD_REQUEST.value(), "INVALID_START_DATE_FORMAT"),
    INVALID_END_DATE_FORMAT(HttpStatus.BAD_REQUEST.value(), "INVALID_END_DATE_FORMAT"),

    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "COMPANY_NOT_FOUND"),

    // 나머지 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
