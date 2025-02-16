package org.ktb.dev.assignment.core.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiKeyRateLimiter {
    private final Map<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    // Rate Limit 상수 정의
    public static final int MAX_REQUESTS = 10;
    public static final int WINDOW_SECONDS = 10;
    private static final double RATE = (double) MAX_REQUESTS / WINDOW_SECONDS;

    public RateLimiter getLimiter(String apiKey) {
        return limiters.computeIfAbsent(apiKey, k -> RateLimiter.create(RATE));
    }

    public boolean tryAcquire(String apiKey) {
        return getLimiter(apiKey).tryAcquire();
    }
}
