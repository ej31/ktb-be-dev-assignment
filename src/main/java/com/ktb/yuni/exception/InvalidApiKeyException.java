package com.ktb.yuni.exception;

public class InvalidApiKeyException extends IllegalArgumentException {
    public InvalidApiKeyException() {
        super("API key가 누락되었거나 올바르지 않습니다.");
    }

    public InvalidApiKeyException(String message) {
        super(message);
    }
}
