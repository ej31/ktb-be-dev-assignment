package com.urung.ktbbedevassignment.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BadRequestException extends RuntimeException {
    private ErrorResponseStatus status;

    public BadRequestException(ErrorResponseStatus status){
        this.status = status;
    }
    public ErrorResponseStatus getStatus(){
        return this.status;
    }

}
