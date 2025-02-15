package ktb.mason.be_assignment.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 응답 설계 이유
 * 1. 일관성 있는 응답 구조
 * 응답이 성공/실패 여부와 관계없이 동일한 구조를 유지할 수 있도록 하였습니다.
 * 2. 클라이언트 고려
 * status에 따라서 응답을 쉽게 파싱할 수 있습니다.
 */


@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse<T> {

    String status;
    T data;
    ErrorResponse error;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .status("success")
                .data(data)
                .build();
    }

    public static BaseResponse<Void> error(ErrorResponse e) {
        return BaseResponse.<Void>builder()
                .status("error")
                .error(e)
                .build();
    }
}
