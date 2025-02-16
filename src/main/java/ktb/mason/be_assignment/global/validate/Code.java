package ktb.mason.be_assignment.global.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodeValidator.class)
public @interface Code {

	String message() default "";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}