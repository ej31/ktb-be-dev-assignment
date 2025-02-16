package org.ktb.stock.global.error.exception;

import lombok.extern.slf4j.Slf4j;
import org.ktb.stock.global.common.CommonResponse;
import org.ktb.stock.global.error.code.ErrorCode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        if (errorCode.isServerError()) {
            log.error("BusinessException: {}", e.getMessage(), e);
        } else {
            log.warn("BusinessException: {}", e.getMessage(), e);
        }

        return CommonResponse.error(errorCode);
    }

    // x-api-key 요청 헤더가 오지 않을 때
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<CommonResponse<Void>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.warn("Missing header: {}", e.getMessage());
        return CommonResponse.error(ErrorCode.MISSING_API_KEY);
    }

    // 존재하지 않는 엔드포인트로 요청이 들어왔을 때
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("No resource found: {}", e.getMessage());
        return CommonResponse.error(ErrorCode.NOT_FOUND_API);
    }

    // 회사 코드로 있는 회사인지 확인하는 중 단건 조회 에러가 났을 때
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<CommonResponse<Void>> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.warn("No data found: {}", e.getMessage());
        return CommonResponse.error(ErrorCode.INVALID_COMPANY_CODE);
    }

    // 예상치 못한 서버 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return CommonResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
