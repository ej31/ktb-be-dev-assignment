package org.ktb.ktbbedevassignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

/*
    특정 응답 값을 설계한 이유
    일관된 특정 으답 값을 가짐으로 사용자에게 예측을 가능하게 도울 수 있습니다.
    사용자의 코드도 일관적으로 관리할 수 있습니다.
    오류 처리 시에도 중앙에서 일관적으로 처리할 수 있습니다.

    * API 응답을 담는 DTO 클래스
    * 성공, 실패 시 공통적인 형태를 가지기 위해 ApiResponse 클래스를 사용
    * timestamp 를 통해 응답 시간을 기록
    * status 를 통해 HTTP 상태 코드를 기록
    * message 를 통해 응답 메시지를 기록
    * data 를 통해 응답 데이터를 기록 (성공 시 데이터가 있거나 빈 배열이며, 실패 시 반환 결과에서 제외될 수 있습니다.)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        int status,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(LocalDateTime.now(), HttpStatus.OK.value(), "Success", data);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(LocalDateTime.now(), status.value(), message, null);
    }
}
