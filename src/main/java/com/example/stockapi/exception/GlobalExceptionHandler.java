package com.example.stockapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 비즈니스 예외 처리 (400, 403)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
        log.warn("Business Exception: {}", ex.getMessage());
        return createErrorResponse(ex.getStatus(), "Business Error", ex.getMessage());
    }

    // 시스템 예외 처리 (500)
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Map<String, String>> handleSystemException(SystemException ex) {
        log.error("System Exception: {}", ex.getMessage(), ex);
        return createErrorResponse(ex.getStatus(), "System Error", ex.getMessage());
    }

    // 잘못된 요청 파라미터 (400 Bad Request) - 날짜 형식 오류 포함
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid request parameter: " + ex.getName();

        if (ex.getRequiredType() != null && ex.getRequiredType().equals(java.time.LocalDate.class)) {
            message = "Invalid date format. Please use 'yyyy-MM-dd'.";
        }

        log.warn("Invalid Parameter: {}", message);
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", message);
    }

    // 존재하지 않는 API 요청 (404 Not Found)
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException() {
        log.warn("API Not Found");
        return createErrorResponse(HttpStatus.NOT_FOUND, "API Not Found", "존재하지 않는 API 입니다.");
    }

    // 기타 예외 처리 (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Unexpected error occurred.");
    }

    // 공통 에러 응답 생성 메서드
    private ResponseEntity<Map<String, String>> createErrorResponse(HttpStatus status, String error, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", error);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}
