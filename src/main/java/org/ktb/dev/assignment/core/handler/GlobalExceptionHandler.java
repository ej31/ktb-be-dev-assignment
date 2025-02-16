package org.ktb.dev.assignment.core.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.BusinessException;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.core.exception.ErrorCode;
import org.ktb.dev.assignment.core.handler.validation.ValidationExceptionHandler;
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

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String LOG_FORMAT = "[{}] {}";
    private final ValidationExceptionHandler validationExceptionHandler;

    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.error(LOG_FORMAT, "BusinessException", ex.getMessage(), ex);
        return createErrorResponse(ex.getErrorCode(),
                ErrorResponse.of(ex.getErrorCode(), ex.getMessage()));
    }

    /**
     * 유효성 검증 예외 처리
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            DateTimeParseException.class,
            MethodArgumentTypeMismatchException.class
    })
    protected ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        log.error(LOG_FORMAT, "ValidationException", ex.getMessage(), ex);

        /*
        Required type: List<org.ktb.dev.assignment.core.response.ErrorResponse.ValidationError>
        Provided: ErrorResponse

         */
        List<ErrorResponse.ValidationError> validationErrors =
                validationExceptionHandler.handleException(ex);

        return createErrorResponse(
                CustomErrorCode.INVALID_INPUT_VALUE,
                ErrorResponse.of(CustomErrorCode.INVALID_INPUT_VALUE, validationErrors)
        );
    }

    /**
     * 필수값 누락 예외 처리
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
                        CustomErrorCode.MISSING_REQUIRED_PARAMETER.formatMessage(ex.getParameterName()),
                        null
                )
        );

        return createErrorResponse(
                CustomErrorCode.MISSING_REQUIRED_PARAMETER,
                ErrorResponse.of(CustomErrorCode.MISSING_REQUIRED_PARAMETER, validationErrors)
        );
    }

    /**
     * @Valid 검증 예외 처리
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
                .map(error -> ErrorResponse.ValidationError.of(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()
                ))
                .collect(Collectors.toList());

        return createErrorResponse(
                CustomErrorCode.INVALID_REQUEST_BODY,
                ErrorResponse.of(CustomErrorCode.INVALID_REQUEST_BODY, validationErrors)
        );
    }

    /**
     * 예상치 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        log.error(LOG_FORMAT, "UnexpectedException", ex.getMessage(), ex);
        return createErrorResponse(
                CustomErrorCode.INTERNAL_SERVER_ERROR,
                ErrorResponse.of(CustomErrorCode.INTERNAL_SERVER_ERROR)
        );
    }

    private <T> ResponseEntity<T> createErrorResponse(ErrorCode errorCode, T body) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(body);
    }
}