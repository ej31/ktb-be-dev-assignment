package org.ktb.dev.assignment.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {
    /*
    **요구사항에 적힌 커스텀 에러 목록!**
    * 초과 시 429 Too Many Requests 등의 상태 코드를 반환하고 에러 메시지에 제한 정보(몇 초 내 N건)를 담아주면 좋습니다.
    * 필수 파라미터 누락, 유효성 검사, API 키 누락 등 다양한 케이스를 테스트해주세요.
     * 유효성 검사를 통해 고객에게 어떻게 호출해야 하는지 직간접적으로 안내할 수 있도록 합니다.
    * 만약 요청에 키가 없다고 판단 될 경우 400, 값이 아래 API Key가 아닌 경우, 403 에러를 반환해주시기 바랍니다.
    * 마찬가지로 Endopoint에서 필수 파라미터로 정의하신 파라미터가 오지 않을 경우 400 에러를 반환해주시면 되며,
    * 존재하지 않는 API를 호출 할 경우, 404 에러를 반환해주시기 바랍니다.
     */

    // 400 Bad Request
    MISSING_REQUIRED_PARAMETER(400, "E001", "필수 파라미터가 누락되었습니다."),
    INVALID_PARAMETER_VALUE(400, "E002", "잘못된 파라미터 값입니다."),
    MISSING_API_KEY(400, "E003", "API 키가 누락되었습니다."),
    INVALID_REQUEST_BODY(400, "E004", "잘못된 요청 본문입니다."),
    INVALID_INPUT_VALUE(400, "E005", "잘못된 입력값입니다."),

    // 403 Forbidden
    INVALID_API_KEY(403, "E101", "유효하지 않은 API 키입니다."),
    ACCESS_DENIED(403, "E102", "접근이 거부되었습니다."),

    // 404 Not Found
    API_NOT_FOUND(404, "E201", "요청한 API를 찾을 수 없습니다."),
    RESOURCE_NOT_FOUND(404, "E202", "요청한 리소스를 찾을 수 없습니다."),

    // 429 Too Many Requests
    RATE_LIMIT_EXCEEDED(429, "E301", "요청 제한을 초과했습니다. {limit}건/{duration}초 제한"),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500, "E901", "서버 내부 오류가 발생했습니다.");


    private final int status;
    private final String code;
    private final String message;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}