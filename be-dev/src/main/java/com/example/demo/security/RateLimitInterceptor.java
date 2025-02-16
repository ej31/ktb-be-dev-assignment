package com.example.demo.security;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component 
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int REQUEST_LIMIT = 10; // 최대 요청 개수
    private static final long TIME_WINDOW = 10 * 1000; // 10초 (밀리초)

    // API 키별 요청 기록 저장 (메모리 기반)
    private final Map<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("x-api-key"); // API 키 가져오기

        if (apiKey == null || apiKey.isEmpty()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing API Key");
            return false;
        }

        // 현재 시간 기준으로 요청 제한 검사
        long now = Instant.now().toEpochMilli();
        requestLogs.putIfAbsent(apiKey, new LinkedList<>());
        Deque<Long> timestamps = requestLogs.get(apiKey);

        synchronized (timestamps) {
            // 10초보다 오래된 요청 제거
            while (!timestamps.isEmpty() && now - timestamps.peekFirst() > TIME_WINDOW) {
                timestamps.pollFirst();
            }

            // 요청 개수 확인
            if (timestamps.size() >= REQUEST_LIMIT) {
                long retryAfter = (timestamps.peekFirst() + TIME_WINDOW - now) / 1000; // 초 단위 변환
                response.setHeader("Retry-After", String.valueOf(retryAfter));
                response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Rate limit exceeded. Try again in " + retryAfter + " seconds.");
                return false;
            }

            // 현재 요청 추가
            timestamps.addLast(now);
        }

        return true;
    }

    public synchronized void resetRateLimit() {
        requestLogs.clear();
    }
}