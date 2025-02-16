package org.ktb.ktbbedevassignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CompanyNotFoundException extends ResponseStatusException {
    public CompanyNotFoundException(String companyCode) {
        super(HttpStatus.NOT_FOUND, "해당 기업이 존재하지 않습니다: " + companyCode);
    }
}
