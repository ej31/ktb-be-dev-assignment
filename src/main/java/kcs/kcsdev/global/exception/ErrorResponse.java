package kcs.kcsdev.global.exception;

import kcs.kcsdev.global.exception.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 공통 에러 응답 포맷을 정의 모든 에러 응답이 일관된 형식을 가짐
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

      /**
       * - errorCode: 오류의 세부적인 원인을 명확히 구분. - httpStatus: HTTP 상태 코드를 포함하여 클라이언트가 오류의 심각성을 이해할 수 있도록
       * 한다. - errorMessage: 사용자 친화적인 오류 메시지를 제공하여 문제를 쉽게 이해할 수 있도록 한다.
       */
      private ErrorCode errorCode;
      private HttpStatus httpStatus;
      private String errorMessage;

}
