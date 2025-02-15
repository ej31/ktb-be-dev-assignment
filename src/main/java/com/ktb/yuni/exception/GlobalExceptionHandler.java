package com.ktb.yuni.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException e) {
        String missingParam = e.getParameterName();
        Map<String, String> error = new HashMap<>();
        error.put("error", "필수 파라미터 '" + missingParam + "'이(가) 누락되었습니다.");
        return ResponseEntity.badRequest().body(error);
    }
}
