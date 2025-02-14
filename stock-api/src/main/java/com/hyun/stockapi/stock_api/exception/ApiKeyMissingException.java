package com.hyun.stockapi.stock_api.exception;


public class ApiKeyMissingException extends RuntimeException {
    public ApiKeyMissingException(String message) {
        super(message);
    }
}