package org.ktb.dev.assignment.core.ratelimit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {
    @Bean
    public ApiKeyRateLimiter apiKeyRateLimiter() {
        return new ApiKeyRateLimiter();
    }
}