package org.ktb.stock.global.common;

import lombok.*;
import org.ktb.stock.global.error.code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T>{
    private boolean success; // 요청의 성공 여부를 나타내기 위함
    private String message; // 사용자나 개발자가 확인할 수 있는 설명을 제공하기 위함
    private T data; // 데이터를 실제로 담는 필드

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .message("데이터를 가져오는데 성공했습니다.")
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(false)
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}
