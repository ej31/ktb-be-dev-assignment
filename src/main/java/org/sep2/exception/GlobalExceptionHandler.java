package org.sep2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequestMapping(produces = "application/json") // 모든 응답을 JSON으로 반환
public class GlobalExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        response.put("data", null);
        return new ResponseEntity<>(response, status);
    }

    // 400: API Key 없는 경우
    @ExceptionHandler(ApiKeyMissingException.class)
    public ResponseEntity<Object> handleApiKeyMissingException(ApiKeyMissingException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 403: API Key가 유효하지 않는 경우
    @ExceptionHandler(ApiKeyInvalidException.class)
    public ResponseEntity<Object> handleApiKeyInvalidException(ApiKeyInvalidException ex) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // 404: 존재하지 않는 API 요청
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }


}
