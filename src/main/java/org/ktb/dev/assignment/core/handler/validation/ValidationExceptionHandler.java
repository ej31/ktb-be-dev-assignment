package org.ktb.dev.assignment.core.handler.validation;

import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.core.handler.validation.strategy.ConstraintViolationStrategy;
import org.ktb.dev.assignment.core.handler.validation.strategy.DateTimeParseStrategy;
import org.ktb.dev.assignment.core.handler.validation.strategy.DefaultExceptionStrategy;
import org.ktb.dev.assignment.core.handler.validation.strategy.ExceptionStrategy;
import org.ktb.dev.assignment.core.handler.validation.strategy.TypeMismatchStrategy;
import org.ktb.dev.assignment.core.response.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Component
@Slf4j
public class ValidationExceptionHandler {

    private final Map<Class<? extends Exception>, ExceptionStrategy> strategyMap;

    public ValidationExceptionHandler() {
        strategyMap = new HashMap<>();
        strategyMap.put(ConstraintViolationException.class, new ConstraintViolationStrategy());
        strategyMap.put(MethodArgumentTypeMismatchException.class, new TypeMismatchStrategy());
        strategyMap.put(DateTimeParseException.class, new DateTimeParseStrategy());
    }

    // ErrorResponse 대신 List<ValidationError> 반환하도록 수정
    public List<ErrorResponse.ValidationError> handleException(Exception ex) {
        return strategyMap
                .getOrDefault(ex.getClass(), new DefaultExceptionStrategy())
                .handle(ex);
    }
}