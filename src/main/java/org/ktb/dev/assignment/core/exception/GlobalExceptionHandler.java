package org.ktb.dev.assignment.core.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
        return handleExceptionInternal(ex.getErrorCode());
    }

    /**
     * 유효성 검증(@Valid) 예외 처리
     * NotNull은 우선 무시!
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
}