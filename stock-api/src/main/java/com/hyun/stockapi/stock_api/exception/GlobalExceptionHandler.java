package com.hyun.stockapi.stock_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        return "서버 에러: " + ex.getMessage();
    }
}
