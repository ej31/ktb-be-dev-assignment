package org.example.stockapitest.exception;

public class ApiKeyException extends RuntimeException {
    private int status;

    public ApiKeyException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
