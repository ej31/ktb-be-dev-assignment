package com.example.stockApi.exception;

import lombok.Getter;
import com.example.stockApi.exception.ErrorCode;
@Getter
public class ApiException extends RuntimeException {
    private final int statusCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatusCode();
    }
}
