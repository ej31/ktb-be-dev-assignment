package com.urung.ktbbedevassignment.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final boolean isSuccess;
    private final int errorCode;
    private final String message;

    public ErrorResponse(ErrorResponseStatus status){
        this.isSuccess = status.isSuccess();
        this.errorCode = status.getErrorCode();
        this.message = status.getMessage();
    }
}
