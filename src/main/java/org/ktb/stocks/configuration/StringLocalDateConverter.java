package org.ktb.stocks.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RequiredArgsConstructor
@Component
public class StringLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            // 문자열을 LocalDate로 변환
            return LocalDate.parse(source, formatter);
        } catch (DateTimeParseException e) {
            // 포맷이 맞지 않는 경우 IllegalArgumentException 던짐
            throw new IllegalArgumentException("Invalid date format: " + source, e);
        }
    }
}
