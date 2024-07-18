package kcs.kcsdev.global.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
      /**
       * 400 Bad Request
       */
      INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
      MISSING_API_KEY(HttpStatus.BAD_REQUEST, "API 키가 누락되었습니다."),

      /**
       * 403 Forbidden
       */
      ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
      INVALID_API_KEY(HttpStatus.FORBIDDEN, "유효하지 않은 API 키입니다."),

      /**
       * 404 Not Found
       */
      RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
      COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "회사 정보를 찾을 수 없습니다."),
      HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "주식 정보를 찾을 수 없습니다."),

      /**
       * 500 Internal Server Error
       */
      INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다.");

      private final HttpStatus httpStatus;
      private final String description;
}
