package com.urung.ktbbedevassignment.global.handler;

import com.urung.ktbbedevassignment.global.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Exception
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> BadRequestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getStatus()));
    }

    //403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> ForbiddenException(ForbiddenException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getStatus()));
    }

    //404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> NotFoundException(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getStatus()));
    }

    //500
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> GlobalException(GlobalException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getStatus()));
    }
}
