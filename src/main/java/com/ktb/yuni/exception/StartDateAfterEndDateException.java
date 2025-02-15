package com.ktb.yuni.exception;

public class StartDateAfterEndDateException extends RuntimeException {
    public StartDateAfterEndDateException() {
        super(String.format("startDate 는 endDate 보다 이후일 수 없습니다."));
    }
}