package org.sep2.exception;

public class ApiKeyMissingException extends RuntimeException {
    public ApiKeyMissingException(String message) {
        super(message);
    }
}
