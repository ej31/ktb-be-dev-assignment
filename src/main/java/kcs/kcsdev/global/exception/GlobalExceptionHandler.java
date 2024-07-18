package kcs.kcsdev.global.exception;

import static kcs.kcsdev.global.exception.type.ErrorCode.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

      /**
       * GlobalException 발생 시, 직접 정의한 일관된 에러 응답 포맷을 사용하여 클라이언트에게 응답.
       *
       * @param e 발생한 GlobalException
       * @return ErrorResponse  에러 응답 포맷
       */
      @ExceptionHandler(GlobalException.class)
      public ErrorResponse handleCustomException(GlobalException e) {
            log.error("{} is occurred.", e.getErrorCode());

            return new ErrorResponse(e.getErrorCode(), e.getErrorCode().getHttpStatus()
                    , e.getErrorCode().getDescription());
      }

      /**
       * 예상치 못한 Exception 발생 시, 내부 서버 오류에 대한 일관된 에러 응답 포맷을 사용하여 클라이언트에게 응답.
       *
       * @param e 발생한 Exception
       * @return ErrorResponse 에러 응답 포맷
       */
      @ExceptionHandler(Exception.class)
      public ErrorResponse exceptionHandler(Exception e) {
            log.error("Exception is occurred", e);

            return new ErrorResponse(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
                    INTERNAL_SERVER_ERROR.getDescription());
      }

}
