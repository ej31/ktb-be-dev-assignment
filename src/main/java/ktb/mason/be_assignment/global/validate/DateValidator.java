package ktb.mason.be_assignment.global.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ktb.mason.be_assignment.global.api.AppHttpStatus;
import org.springframework.util.StringUtils;

public class DateValidator implements ConstraintValidator<Date, String> {
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(date)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(AppHttpStatus.BLANK_NOT_ALLOWED.getMessage()).addConstraintViolation();
			return false;
		}

		if (!isValidFormat(date, DATE_PATTERN) && !isValidFormat(date, DATETIME_PATTERN)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(AppHttpStatus.INVALID_DATE_FORMAT.getMessage()).addConstraintViolation();
			return false;
		}

		return true;
	}

	private boolean isValidFormat(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false); // lenient 모드를 false로 설정하여 유효하지 않은 날짜를 허용하지 않음
		try {
			sdf.parse(date); // 유효하지 않은 날짜 형식일 경우 ParseException이 발생함
		} catch (ParseException e) {
			return false; // 유효하지 않은 날짜 형식
		}
		return date.length() == pattern.length(); // 유효한 날짜 형식
	}
}