package org.ktb.stock.global.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // API KEY가 잘못된 경우
    MISSING_API_KEY(HttpStatus.BAD_REQUEST.value(), "API Key가 없습니다."),
    INVALID_API_KEY(HttpStatus.FORBIDDEN.value(), "API Key가 일치하지 않습니다."),

    // 잘못된 요청이 올 경우
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST.value(), "필수 요청 값이 누락되었습니다."),
    INVALID_COMPANY_CODE(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회사 코드입니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST.value(), "날짜는 yyyy-MM-dd 형식이어야 합니다"),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST.value(), "시작 날짜가 종료 날짜보다 늦습니다."),

    // 리소스를 찾을 수 없는 경우
    NOT_FOUND_API(HttpStatus.NOT_FOUND.value(), "호출한 API가 존재하지 않습니다."),
    NOT_FOUND_STOCK_DATA(HttpStatus.NOT_FOUND.value(), "해당 기간의 주식 데이터를 찾을 수 없습니다."),

    // 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;

    public boolean isServerError() {
        return this.status == HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}

/**
 * 헤더 API 키 시나리오
 * 1. 요청 헤더에 API 키 누락 (400)
 * 2. 요청 헤더로 온 API 키 값이 서비스 API 키 값과 불일치 (403)
 *
 * 사용자 요청 에러 시나리오
 * 1. 필수 파라미터 누락 (400)
 * 2. 존재하지 않는 회사 코드 입력 (400)
 * 3. 잘못된 날짜 형식 (400)
 * 4. 시작 날짜가 종료 날 보다 늦는 경우 (400)
 *
 * 리소스가 없을 때
 * 1. 해당 기간 동안 주식 데이터가 없음
 * 2. 호출한 API 없음
 * 서버 오류
 * 1. 500 에러 반환
 */
