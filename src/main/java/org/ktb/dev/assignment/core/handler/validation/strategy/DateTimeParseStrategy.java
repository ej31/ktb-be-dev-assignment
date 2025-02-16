package org.ktb.dev.assignment.core.handler.validation.strategy;

import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.core.response.ErrorResponse;
import java.time.format.DateTimeParseException;
import java.util.List;
@Slf4j
public class DateTimeParseStrategy implements ExceptionStrategy {
    @Override
    public List<ErrorResponse.ValidationError> handle(Exception ex) {
        DateTimeParseException dtpe = (DateTimeParseException) ex;
        return List.of(
                ErrorResponse.ValidationError.of(
                        "date",
                        CustomErrorCode.INVALID_DATE_FORMAT.getMessage(),
                        dtpe.getParsedString()
                )
        );
    }
}