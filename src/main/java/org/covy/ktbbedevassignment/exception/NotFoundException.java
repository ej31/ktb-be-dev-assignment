package org.covy.ktbbedevassignment.exception;

// 404에러 처리

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

