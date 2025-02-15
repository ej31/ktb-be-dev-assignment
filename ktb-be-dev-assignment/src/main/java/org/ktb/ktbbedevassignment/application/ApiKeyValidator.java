package org.ktb.ktbbedevassignment.application;

import org.ktb.ktbbedevassignment.exception.InvalidApiKeyException;
import org.ktb.ktbbedevassignment.exception.NotMatchedApiKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyValidator {

    private final String validApiKey;

    public ApiKeyValidator(@Value("${api.key}") String validApiKey) {
        this.validApiKey = validApiKey;
    }

    public void validateApiKey(String apiKey) {
        if (apiKey == null) {
            throw new InvalidApiKeyException();
        }

        if (!apiKey.equals(validApiKey)) {
            throw new NotMatchedApiKeyException();
        }
    }
}
