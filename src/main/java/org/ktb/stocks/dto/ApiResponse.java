package org.ktb.stocks.dto;

import lombok.Getter;

@Getter
public class ApiResponse {
    private final String message;
    private final Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse("OK", data);
    }
}
