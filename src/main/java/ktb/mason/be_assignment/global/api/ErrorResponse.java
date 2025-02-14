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
public class ErrorResponse {
    int code;
    String message;

    public static ErrorResponse of(ApiException e) {
        return ErrorResponse.builder()
                .code(e.getStatus().getStatusCode())
                .message(e.getStatus().getMessage())
                .build();
    }
    public static ErrorResponse of(RuntimeException e) {
        return ErrorResponse.builder()
                .code(500)
                .message(e.getMessage())
                .build();
    }
}
