package com.example.stockapi.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private final HttpStatus status;

    public SystemException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
