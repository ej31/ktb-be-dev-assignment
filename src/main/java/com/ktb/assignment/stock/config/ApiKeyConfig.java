package com.ktb.assignment.stock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyConfig {
    @Value("${api.key:#{null}}")
    private String apiKey;

    public String getApiKey() {
        return apiKey != null ? apiKey : System.getenv("STOCK_API_KEY");
    }
}