package ktb.mason.be_assignment.global.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public BaseResponse<Void> runtimeExceptionHandler(RuntimeException e) {
		return BaseResponse.error(ErrorResponse.of(e));
	}
	@ExceptionHandler(ApiException.class)
	public BaseResponse<Void> apiExceptionHandler(ApiException e) {
		return BaseResponse.error(ErrorResponse.of(e));
	}
}