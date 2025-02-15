package org.ktb.dev.assignment.core.Handler;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.BusinessException;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.core.exception.ErrorCode;
import org.ktb.dev.assignment.core.response.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 전역 예외 처리를 담당하는 핸들러
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        log.error("[BusinessException] {}", ex.getMessage(), ex);
        return handleExceptionInternal(ex.getErrorCode(), ex.getMessage());
    }

    /**
     * 유효성 검증(@Valid) 예외 처리
     */
    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        log.error("[ValidationException] {}", ex.getMessage(), ex);
        return handleValidationExceptionInternal(ex);
    }

    /**
     * 날짜 형식 변환 실패 예외 처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        log.error("[DateFormatException] {}", ex.getMessage(), ex);

        // 날짜 형식 에러인 경우에만 특정 메시지 처리
        if (LocalDate.class.isAssignableFrom(ex.getRequiredType())) {
            return handleExceptionInternal(CustomErrorCode.INVALID_DATE_FORMAT);
        }

        return handleExceptionInternal(CustomErrorCode.INVALID_INPUT_VALUE);
    }

    /**
     * 날짜 파싱 실패 예외 처리
     */
    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(
            DateTimeParseException ex) {
        log.error("[DateParseException] {}", ex.getMessage(), ex);
        return handleExceptionInternal(CustomErrorCode.INVALID_DATE_FORMAT);
    }

    /**
     * Bean Validation 관련 예외 처리 (@NotNull, @Past 등)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        log.error("[ConstraintViolationException] {}", ex.getMessage(), ex);

        List<ErrorResponse.ValidationError> validationErrorList = ex.getConstraintViolations()
                .stream()
                .map(violation -> ErrorResponse.ValidationError.of(
                        extractFieldName(violation.getPropertyPath().toString()),
                        violation.getMessage(),
                        violation.getInvalidValue()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.status(CustomErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(ErrorResponse.of(CustomErrorCode.INVALID_INPUT_VALUE, validationErrorList));
    }

    /**
     * 날짜 형식 변환 실패 예외 처리 - 메시지 개선
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            @NonNull TypeMismatchException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        log.error("[TypeMismatchException] {}", ex.getMessage(), ex);

        if (ex.getRequiredType() != null && LocalDate.class.isAssignableFrom(ex.getRequiredType())) {
            List<ErrorResponse.ValidationError> validationErrorList = List.of(
                    ErrorResponse.ValidationError.of(
                            ex.getPropertyName(),
                            "날짜 형식은 'yyyy-MM-dd'이어야 합니다",
                            ex.getValue()
                    )
            );
            return ResponseEntity.status(CustomErrorCode.INVALID_DATE_FORMAT.getStatus())
                    .body(ErrorResponse.of(CustomErrorCode.INVALID_DATE_FORMAT, validationErrorList));
        }

        return handleExceptionInternal(CustomErrorCode.INVALID_INPUT_VALUE);
    }

    /**
     * 예상치 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.error("[UnexpectedException] {}", ex.getMessage(), ex);
        return handleExceptionInternal(CustomErrorCode.INTERNAL_SERVER_ERROR);
    }

    // 에러 응답 생성
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, message));
    }

    // 유효성 검증 에러 응답 생성
    private ResponseEntity<Object> handleValidationExceptionInternal(BindException bindException) {
        List<ErrorResponse.ValidationError> validationErrorList = bindException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ResponseEntity.status(CustomErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(ErrorResponse.of(CustomErrorCode.INVALID_INPUT_VALUE, validationErrorList));
    }

    // 필드명 추출 헬퍼 메소드
    private String extractFieldName(String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        return parts[parts.length - 1];
    }
}