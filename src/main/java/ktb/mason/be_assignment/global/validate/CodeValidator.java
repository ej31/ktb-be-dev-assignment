package ktb.mason.be_assignment.global.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ktb.mason.be_assignment.global.api.AppHttpStatus;
import org.springframework.util.StringUtils;

public class CodeValidator implements ConstraintValidator<Code, String> {

	@Override
	public boolean isValid(String code, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(code)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(AppHttpStatus.BLANK_NOT_ALLOWED.getMessage()).addConstraintViolation();
			return false;
		}

		return true;
	}
}