package org.ktb.ktbbedevassignment.config;

import java.time.Clock;
import org.ktb.ktbbedevassignment.util.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    private static final int MAX_REQUESTS = 10;
    private static final long TIME_WINDOW_MS = 10_000;

    @Bean
    public RateLimiter rateLimiter() {
        return new RateLimiter(MAX_REQUESTS, TIME_WINDOW_MS, Clock.systemDefaultZone());
    }
}
