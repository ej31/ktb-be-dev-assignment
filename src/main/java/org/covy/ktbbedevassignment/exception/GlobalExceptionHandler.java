package org.covy.ktbbedevassignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //  400 BAD REQUEST - 잘못된 요청 값 예외 처리
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, DateTimeParseException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "잘못된 요청 형식입니다. 요청 값을 확인해주세요.");
    }

    //  403 FORBIDDEN - API Key 인증 실패
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<Object> handleForbidden(IllegalAccessException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "API Key가 유효하지 않습니다.");
    }

    //  404 NOT FOUND - 데이터가 존재하지 않을 경우
    @ExceptionHandler({IllegalArgumentException.class, NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  500 INTERNAL SERVER ERROR - 예상하지 못한 서버 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
    }

    //  공통 에러 응답 생성 메서드
    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}


