package com.ktb.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ktb.assignment.exception.CommonException;
import com.ktb.assignment.exception.ErrorCode;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto<T>(
        boolean success,
        T data,
        ExceptionDto error
) {
    // 성공 응답
    public static <T> ResponseDto<T> ok(T data) {
        return new ResponseDto<>(true, data, null);
    }

    // 실패 응답 (커스텀 예외)
    public static ResponseDto<Object> fail(CommonException e) {
        return new ResponseDto<>(false, null, new ExceptionDto(e.getErrorCode()));
    }

    // 실패 응답 (기본 예외)
    public static ResponseDto<Object> fail(ErrorCode errorCode) {
        return new ResponseDto<>(false, null, new ExceptionDto(errorCode));
    }
}
