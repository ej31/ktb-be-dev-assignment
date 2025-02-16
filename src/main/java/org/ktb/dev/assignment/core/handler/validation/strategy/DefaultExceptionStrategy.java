package org.ktb.dev.assignment.core.handler.validation.strategy;

import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.core.response.ErrorResponse;

import java.util.List;

@Slf4j
public class DefaultExceptionStrategy implements ExceptionStrategy {
    @Override
    public List<ErrorResponse.ValidationError> handle(Exception ex) {
        return List.of(
                ErrorResponse.ValidationError.of(
                        "unknown",
                        CustomErrorCode.INVALID_INPUT_VALUE.getMessage(),
                        null
                )
        );
    }
}