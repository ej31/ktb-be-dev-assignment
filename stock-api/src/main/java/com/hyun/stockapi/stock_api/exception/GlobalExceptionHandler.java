package com.hyun.stockapi.stock_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // API Key가 없는 경우 → 400
    @ExceptionHandler(ApiKeyMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleMissingApiKey(ApiKeyMissingException ex) {
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error",ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // API Key가 잘못된 경우 → 403
    @ExceptionHandler(ApiKeyInvalidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String,String>> handleInvalidApiKey(ApiKeyInvalidException ex) {
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // 요청 파라미터 잘못됨 → 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error",ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //유효성 검사 실패 -> 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    //그외 오류 ->500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
