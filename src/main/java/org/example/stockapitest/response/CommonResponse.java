package org.example.stockapitest.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CommonResponse<T> {
    private final int status;
    private final String message;
    private final T data;

    public CommonResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<?> createResponse(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status).body(new CommonResponse<>(status.value(), message, data));
    }
}
