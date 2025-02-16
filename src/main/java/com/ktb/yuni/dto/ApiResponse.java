package com.ktb.yuni.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success; // API 요청 성공 여부
    private String message; // 응답 메시지
    private T data; // 응답 데이터
    private int status;

    // 성공 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, 200);
    }

    // 실패 응답
    public static <T> ApiResponse<T> failure(String message, int status) {
        return new ApiResponse<>(false, message, null, status);
    }
}
