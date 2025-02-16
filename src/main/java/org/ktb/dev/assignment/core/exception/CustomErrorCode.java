package org.ktb.dev.assignment.core.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

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
    MISSING_REQUIRED_PARAMETER(400, "E001", CustomErrorMessage.MISSING_PARAMETER),
    INVALID_PARAMETER_VALUE(400, "E002", CustomErrorMessage.INVALID_PARAMETER),
    MISSING_API_KEY(400, "E003", CustomErrorMessage.MISSING_API_KEY),
    INVALID_REQUEST_BODY(400, "E004", CustomErrorMessage.INVALID_REQUEST_BODY),
    INVALID_INPUT_VALUE(400, "E005", CustomErrorMessage.INVALID_INPUT),
    INVALID_DATE_FORMAT(400, "E006", CustomErrorMessage.INVALID_DATE_FORMAT),

    // 403 Forbidden
    INVALID_API_KEY(403, "E101", CustomErrorMessage.INVALID_API_KEY),

    // 404 Not Found
    API_NOT_FOUND(404, "E201", CustomErrorMessage.API_NOT_FOUND),
    RESOURCE_NOT_FOUND(404, "E202", CustomErrorMessage.RESOURCE_NOT_FOUND),
    COMPANY_NOT_FOUND(404, "E203", CustomErrorMessage.COMPANY_NOT_FOUND),

    // 429 Too Many Requests
    RATE_LIMIT_EXCEEDED(429, "E301", CustomErrorMessage.RATE_LIMIT_EXCEEDED),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500, "E901", CustomErrorMessage.INTERNAL_SERVER_ERROR);

    private final int status;
    private final String code;
    private final CustomErrorMessage messageTemplate;

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
        return messageTemplate.getMessage();
    }

    public String getMessage(Object... args) {
        return messageTemplate.formatMessage(args);
    }

    public String formatMessage(Object... args) {
        return MessageFormat.format(getMessage(), args);
    }
}