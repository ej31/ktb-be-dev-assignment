package ktb.mason.be_assignment.global.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppHttpStatus {
    /**
     * 20X : 성공
     */
    OK(200, HttpStatus.OK, "요청이 정상적으로 수행되었습니다."),
    CREATED(201, HttpStatus.CREATED, "리소스를 생성하였습니다."),

    /**
     * 400 : 잘못된 문법으로 인해 요청을 이해할 수 없음
     */
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    FAILED_VALIDATION(400, HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다."),
    BLANK_NOT_ALLOWED(400, HttpStatus.BAD_REQUEST, "공백은 입력할 수 없습니다."),
    INVALID_DATE_FORMAT(400, HttpStatus.BAD_REQUEST, "날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식이어야 합니다."),

    /**
     * 401 : 인증된 사용자가 아님
     */
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    /**
     * 403 : 접근 권한이 없음
     */
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "권한이 없습니다."),

    /**
     * 404 : 응답할 리소스가 없음
     */
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),

    /**
     * 500 : 서버 내부에서 에러가 발생함
     */
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에 에러가 발생했습니다.");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}