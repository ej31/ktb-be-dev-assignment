package ktb.mason.be_assignment.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

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
