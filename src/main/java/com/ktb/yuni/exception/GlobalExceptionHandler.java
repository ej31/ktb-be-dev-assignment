package com.ktb.yuni.exception;

import com.ktb.yuni.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingParams(MissingServletRequestParameterException e) {
        String missingParam = e.getParameterName();
        String message = "필수 파라미터 '" + missingParam + "'이(가) 누락되었습니다.";
        return ResponseEntity.badRequest().body(ApiResponse.failure(message, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    // 날짜 형식 오류
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse<?>> handleDateTimeParseException(DateTimeParseException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.failure("잘못된 날짜 형식입니다. yyyy-MM-dd 형식을 사용하세요.", HttpStatus.BAD_REQUEST.value()));
    }

    // startDate가 endDate 보다 이전이어야 함
    @ExceptionHandler(StartDateAfterEndDateException.class)
    public ResponseEntity<ApiResponse<?>> handleStartDateAfterEndDateException(StartDateAfterEndDateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    // 찾을 수 없는 회사 코드
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleCompanyNotFound(CompanyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
