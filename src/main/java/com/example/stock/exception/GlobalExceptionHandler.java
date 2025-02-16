package com.example.stock.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("error", ex.getErrorCode().name());
//        response.put("message", ex.getMessage());
//        return new ResponseEntity<>(response, ex.getErrorCode().defaultHttpStatus());
//    }

    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "BAD_REQUEST");
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    // 일반 Exception 처리 : 서버 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "INTERNAL_SERVER_ERROR");
        response.put("message", "Something went wrong. Please try again.");
        return ResponseEntity.internalServerError().body(response);
    }
}
