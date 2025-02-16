package com.ktb.assignment.exception;

import com.ktb.assignment.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 존재하지 않는 API 요청 (404)
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ResponseDto<?>> handleNoPageFoundException(Exception e) {
        log.error("handleNoPageFoundException: {}", e.getMessage());

        CommonException exception = new CommonException(ErrorCode.NOT_FOUND_END_POINT);
        return ResponseEntity
                .status(exception.getErrorCode().getHttpStatus()) // ✅ HTTP 상태 코드 직접 설정
                .body(ResponseDto.fail(exception));
    }

    // 필수 요청 파라미터가 누락된 경우 (400)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ResponseDto<?>> handleServletRequestParameterException(MissingServletRequestPartException e) {
        log.error("handleServletRequestParameterException: {}", e.getMessage());

        CommonException exception = new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER);
        return ResponseEntity
                .status(exception.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(exception));
    }

    // 요청 Body가 없는 경우 (400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<?>> handleMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleMessageNotReadableException: {}", e.getMessage());

        CommonException exception = new CommonException(ErrorCode.MISSING_REQUEST_BODY);
        return ResponseEntity
                .status(exception.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(exception));
    }

    // 개발자가 직접 정의한 커스텀 예외
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResponseDto<?>> handleApiException(CommonException e) {
        log.error("handleApiException: {}", e.getMessage());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(e));
    }

    // 서버 내부 오류 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleException(Exception e) {
        log.error("handleException: {}", e.getMessage(), e);

        CommonException exception = new CommonException(ErrorCode.SERVER_ERROR);
        return ResponseEntity
                .status(exception.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(exception));
    }
}
