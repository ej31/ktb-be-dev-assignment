package org.example.stockapitest.exception;

import org.example.stockapitest.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return CommonResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
//        return CommonResponse.createResponse(HttpStatus.NOT_FOUND, "API endpoint not found", null);
//    }

}
