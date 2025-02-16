package com.ktb.yuni.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String companyCode) {
        super("회사 코드 '" + companyCode + "'를 찾을 수 없습니다.");
    }
}
