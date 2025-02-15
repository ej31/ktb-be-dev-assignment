package com.hyun.stockapi.stock_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //잘못된 날짜 형식 입력 ->400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
        Map<String, String> errorResponse = new HashMap<>();
        if(ex.getRequiredType() !=null && ex.getRequiredType().equals(java.time.LocalDate.class)){
            errorResponse.put("error","날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 입력해주세요)");
        } else{
            errorResponse.put("error","잘못된 요청 형식 입니다.");
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //존재하지 않는 요청 값 -> 400
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMissingParams(org.springframework.web.bind.MissingServletRequestParameterException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getParameterName() + " 파라미터가 누락되었습니다.");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
