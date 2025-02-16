package org.ktb.ktbbedevassignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidApiKeyException extends ResponseStatusException {
    public InvalidApiKeyException() {
        super(HttpStatus.BAD_REQUEST, "API Key가 존재하지 않습니다.");
    }
}
