package org.example.stockapitest.exception;

import org.example.stockapitest.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return CommonResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
    }

//    @ExceptionHandler(ApiKeyException.class)
//    public ResponseEntity<?> handleApiKeyException(ApiKeyException e) {
//        return CommonResponse.createResponse(HttpStatus.valueOf(e.getStatus()), e.getMessage(), null);
//    }
}
