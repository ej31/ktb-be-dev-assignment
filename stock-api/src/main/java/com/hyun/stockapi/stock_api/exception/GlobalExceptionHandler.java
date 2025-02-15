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
    public String handleMissingApiKey(ApiKeyMissingException ex) {
        return ex.getMessage();
    }

    // API Key가 잘못된 경우 → 403
    @ExceptionHandler(ApiKeyInvalidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleInvalidApiKey(ApiKeyInvalidException ex) {
        return ex.getMessage();
    }

    // 요청 파라미터 잘못됨 → 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    //잘못된 날짜 형식 입력 ->400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
        Map<String, String> error = new HashMap<>();
        if(ex.getRequiredType() !=null && ex.getRequiredType().equals(java.time.LocalDate.class)){
            error.put("error","날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 입력해주세요)");
        } else{
            error.put("error","잘못된 요청 형식 입니다.");
        }
        return ResponseEntity.badRequest().body(error);
    }

    //존재하지 않는 요청 값 -> 400
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMissingParams(org.springframework.web.bind.MissingServletRequestParameterException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getParameterName() + " 파라미터가 누락되었습니다.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        return "서버 에러: " + ex.getMessage();
    }
}
