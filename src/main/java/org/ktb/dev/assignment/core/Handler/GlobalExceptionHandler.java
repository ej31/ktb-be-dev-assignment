package org.ktb.dev.assignment.core.Handler;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.BusinessException;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.core.exception.ErrorCode;
import org.ktb.dev.assignment.core.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String LOG_FORMAT = "[{}] {}";

    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.error(LOG_FORMAT, "BusinessException", ex.getMessage(), ex);
        return createErrorResponse(
                ex.getErrorCode(),
                ErrorResponse.of(ex.getErrorCode(), ex.getMessage())
        );
    }

    /**
     * 필수값 누락 예외처리
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        log.error(LOG_FORMAT, "MissingParameterException", ex.getMessage(), ex);

        List<ErrorResponse.ValidationError> validationErrors = List.of(
                ErrorResponse.ValidationError.of(
                        ex.getParameterName(),
                        String.format("필수 파라미터 %s가 누락되었습니다", ex.getParameterName()),
                        null
                )
        );

        return createErrorResponse(
                CustomErrorCode.INVALID_INPUT_VALUE,
                ErrorResponse.of(CustomErrorCode.INVALID_INPUT_VALUE, validationErrors)
        );
    }

    /**
     * API 검증 관련 모든 예외를 처리하는 통합 핸들러
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            DateTimeParseException.class,
            MethodArgumentTypeMismatchException.class
    })
    protected ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        log.error(LOG_FORMAT, "ValidationException", ex.getMessage(), ex);

        List<ErrorResponse.ValidationError> validationErrors;

        if (ex instanceof ConstraintViolationException cve) {
            validationErrors = cve.getConstraintViolations()
                    .stream()
                    .map(violation -> ErrorResponse.ValidationError.of(
                            extractFieldName(violation.getPropertyPath().toString()),
                            violation.getMessage(),
                            violation.getInvalidValue()
                    ))
                    .collect(Collectors.toList());
        } else if (ex instanceof MethodArgumentTypeMismatchException matme) {
            String field = matme.getName();
            String message = matme.getRequiredType() != null
                    && LocalDate.class.isAssignableFrom(matme.getRequiredType())
                    ? "날짜 형식은 'yyyy-MM-dd'이어야 합니다"
                    : "잘못된 형식입니다";

            validationErrors = List.of(
                    ErrorResponse.ValidationError.of(field, message, matme.getValue())
            );
        } else if (ex instanceof DateTimeParseException dtpe) {
            validationErrors = List.of(
                    ErrorResponse.ValidationError.of(
                            "date",
                            "날짜 형식은 'yyyy-MM-dd'이어야 합니다",
                            dtpe.getParsedString()
                    )
            );
        } else {
            validationErrors = List.of(
                    ErrorResponse.ValidationError.of(
                            "unknown",
                            "잘못된 입력값입니다",
                            null
                    )
            );
        }

        return (ResponseEntity) createErrorResponse(
                CustomErrorCode.INVALID_INPUT_VALUE,
                ErrorResponse.of(CustomErrorCode.INVALID_INPUT_VALUE, validationErrors)
        );
    }

    /**
     * @Valid, @Validated 어노테이션 검증 실패 처리
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        log.error(LOG_FORMAT, "MethodArgumentNotValidException", ex.getMessage(), ex);

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return createErrorResponse(
                CustomErrorCode.INVALID_INPUT_VALUE,
                ErrorResponse.of(CustomErrorCode.INVALID_INPUT_VALUE, validationErrors)
        );
    }

    /**
     * 예상치 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        log.error(LOG_FORMAT, "UnexpectedException", ex.getMessage(), ex);
        return (ResponseEntity) createErrorResponse(
                CustomErrorCode.INTERNAL_SERVER_ERROR,
                ErrorResponse.of(CustomErrorCode.INTERNAL_SERVER_ERROR)
        );
    }

    private <T> ResponseEntity<T> createErrorResponse(ErrorCode errorCode, T body) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(body);
    }

    private String extractFieldName(String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        return parts[parts.length - 1];
    }
}