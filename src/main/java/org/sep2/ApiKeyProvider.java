package org.sep2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyProvider {

    @Value("${api.key}") // application.yml에서 API Key 가져오기
    private String apiKey;

    public boolean isValid(String providedApiKey) {
        return apiKey.equals(providedApiKey);
    }
}
