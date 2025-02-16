package org.ktb.ktbbedevassignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotMatchedApiKeyException extends ResponseStatusException {
    public NotMatchedApiKeyException() {
        super(HttpStatus.FORBIDDEN, "잘못된 API Key입니다.");
    }
}
