package ktb.mason.be_assignment.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    int code;
    String message;
    Map<String, String> messages;

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

    public static ErrorResponse of(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> v : violations) {
            String field = v.getPropertyPath().toString().split("\\.")[1]; // e.g. method.field
            String message = v.getMessageTemplate();
            errors.put(field, message);
        }

        return ErrorResponse.builder()
                .code(400)
                .messages(errors)
                .build();
    }
}
