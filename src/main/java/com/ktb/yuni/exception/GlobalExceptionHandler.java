package com.ktb.yuni.exception;

import com.ktb.yuni.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 필수 파라미터 누락 예외 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingParams(MissingServletRequestParameterException e) {
        String missingParam = e.getParameterName();
        String message = "필수 파라미터 '" + missingParam + "'이(가) 누락되었습니다.";
        log.warn("[400 BAD REQUEST] {}", message, e);
        return ResponseEntity.badRequest().body(ApiResponse.failure(message, HttpStatus.BAD_REQUEST.value()));
    }

    // API 키 관련 예외 처리
    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidApiKeyException(InvalidApiKeyException e) {
        log.warn("[403 FORBIDDEN] API 키 오류: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    // 날짜 형식 오류 처리
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse<?>> handleDateTimeParseException(DateTimeParseException e) {
        log.warn("[400 BAD REQUEST] 잘못된 날짜 형식 입력", e);
        return ResponseEntity.badRequest()
                .body(ApiResponse.failure("잘못된 날짜 형식입니다. yyyy-MM-dd 형식을 사용하세요.", HttpStatus.BAD_REQUEST.value()));
    }

    // startDate가 endDate 보다 이후일 때 예외 처리
    @ExceptionHandler(StartDateAfterEndDateException.class)
    public ResponseEntity<ApiResponse<?>> handleStartDateAfterEndDateException(StartDateAfterEndDateException e) {
        log.warn("[400 BAD REQUEST] 잘못된 날짜 범위: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    // 찾을 수 없는 회사 코드
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleCompanyNotFound(CompanyNotFoundException e) {
        log.warn("[404 NOT FOUND] 회사 코드 오류: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    // IllegalArgumentException 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        log.error("[500 INTERNAL SERVER ERROR] 예상치 못한 오류 발생", e);
        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    // 예상치 못한 RuntimeException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("[400 BAD REQUEST] 잘못된 요청 파라미터", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
