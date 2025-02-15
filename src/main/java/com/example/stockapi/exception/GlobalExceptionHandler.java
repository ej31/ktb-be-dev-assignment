package com.example.stockapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 필수 파라미터 누락 및 기타 BadRequest 예외 처리 (400 Bad Request)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }

    // 잘못된 요청 파라미터 (400 Bad Request) - 날짜 형식 오류 포함
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid request parameter: " + ex.getName();

        if (ex.getRequiredType() != null && ex.getRequiredType().equals(java.time.LocalDate.class)) {
            message = "Invalid date format. Please use 'yyyy-MM-dd'.";
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", message);
    }

    // 존재하지 않는 API 요청 (404 Not Found)
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException() {
        return createErrorResponse(HttpStatus.NOT_FOUND, "API Not Found", "존재하지 않는 API 입니다.");
    }

    // 기타 예외 처리 (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage());
    }

    // 공통 에러 응답 생성 메서드
    private ResponseEntity<Map<String, String>> createErrorResponse(HttpStatus status, String error, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", error);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}
