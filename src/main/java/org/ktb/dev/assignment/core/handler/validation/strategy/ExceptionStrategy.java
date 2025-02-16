package org.ktb.dev.assignment.core.handler.validation.strategy;

import org.ktb.dev.assignment.core.response.ErrorResponse;
import java.util.List;

public interface ExceptionStrategy {
    List<ErrorResponse.ValidationError> handle(Exception ex);
}