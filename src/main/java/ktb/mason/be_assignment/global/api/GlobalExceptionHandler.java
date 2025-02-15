package ktb.mason.be_assignment.global.api;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<BaseResponse<Void>> runtimeExceptionHandler(RuntimeException e) {
		return ResponseEntity
				.status(500)
				.body( BaseResponse.error(ErrorResponse.of(e)));
	}
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<BaseResponse<Void>> apiExceptionHandler(ApiException e) {
		return ResponseEntity
				.status(e.getStatus().getStatusCode())
				.body(BaseResponse.error(ErrorResponse.of(e)));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<BaseResponse<Void>> constraintViolationExceptionHandler(ConstraintViolationException e) {
		return ResponseEntity
				.status(400)
				.body(BaseResponse.error(ErrorResponse.of(e)));
	}
}