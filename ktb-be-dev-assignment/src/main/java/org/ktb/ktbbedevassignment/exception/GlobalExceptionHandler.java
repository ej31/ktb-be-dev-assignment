package org.ktb.ktbbedevassignment.exception;

import org.ktb.ktbbedevassignment.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 없습니다: " + ex.getParameterName()));
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidApiKeyException(InvalidApiKeyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getReason()));
    }

    @ExceptionHandler(NotMatchedApiKeyException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotMatchedApiKeyException(NotMatchedApiKeyException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(HttpStatus.FORBIDDEN, ex.getReason()));
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleCompanyNotFoundException(CompanyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, ex.getReason()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."));
    }
}
