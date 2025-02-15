package com.example.stockApi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.stockApi.exception.ErrorCode;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // API 처리 과정 중 에러 처리
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(Map.of(
                        "statusCode", ex.getStatusCode(),
                        "message", ex.getMessage()
                ));
    }

    // API 에러가 아닌 500 에러 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpectedException(Exception ex) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatusCode())
                .body(Map.of(
                        "statusCode", ErrorCode.INTERNAL_SERVER_ERROR.getStatusCode(),
                        "message", ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                        "details", ex.getMessage()
                ));
    }
}