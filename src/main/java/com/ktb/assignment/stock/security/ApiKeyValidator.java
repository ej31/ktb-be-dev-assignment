package com.ktb.assignment.stock.security;

import com.ktb.assignment.stock.config.ApiKeyConfig;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyValidator {

    private final ApiKeyConfig apiKeyConfig;

    public ApiKeyValidator(ApiKeyConfig apiKeyConfig) {
        this.apiKeyConfig = apiKeyConfig;
    }

    public boolean isValidApiKey(String providedKey) {
        return providedKey != null && providedKey.equals(apiKeyConfig.getApiKey());
    }
}