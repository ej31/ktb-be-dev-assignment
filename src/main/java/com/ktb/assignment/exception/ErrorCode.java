package com.ktb.assignment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400 error
    INVALID_PARAMETER("40000", HttpStatus.BAD_REQUEST, "유효하지 않는 파라미터입니다."),
    MISSING_REQUEST_PARAMETER("40001", HttpStatus.BAD_REQUEST, "필수 파라미터가 누락되었습니다."),
    MISSING_REQUEST_BODY("40002", HttpStatus.BAD_REQUEST, "요청 바디가 누락되었습니다."),
    MISSING_API_KEY_PARAMETER("40003", HttpStatus.BAD_REQUEST, "API Key가 누락되었습니다."),
    INVALID_DATE_FORMAT("40004", HttpStatus.BAD_REQUEST, "날짜 형식이 올바르지 않습니다. (yyyy-MM-dd) 형식을 사용하세요."),

    // 403 error
    ACCESS_DENIED_ERROR("40300", HttpStatus.FORBIDDEN, "API Key가 일치하지 않습니다."),

    // 404 error
    NOT_FOUND_END_POINT("40400", HttpStatus.NOT_FOUND, "존재하지 않는 엔드포인트입니다."),

    // 429 error
    RATE_LIMIT_EXCEEDED("42900", HttpStatus.TOO_MANY_REQUESTS, "API Key 요청 제한 초과 (10초당 최대 10건)"),

    // 500 error
    SERVER_ERROR("50000", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
