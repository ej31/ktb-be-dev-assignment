package org.ktb.ktbbedevassignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RateLimitExceededException extends ResponseStatusException {
    public RateLimitExceededException() {
        super(HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다. 10초 내 최대 10건의 요청만 허용됩니다. 잠시 후 다시 시도해주세요.");
    }
}
