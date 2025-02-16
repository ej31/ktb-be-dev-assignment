package org.sep2.exception;

public class ApiKeyInvalidException extends RuntimeException {
    public ApiKeyInvalidException(String message) {
        super(message);
    }
}
