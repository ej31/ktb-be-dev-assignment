package com.ktb.assignment.util;

import com.ktb.assignment.constant.Constants;
import com.ktb.assignment.exception.CommonException;
import com.ktb.assignment.exception.ErrorCode;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiRateLimiter {

    private final Map<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();

    public void validateRequest(String apiKey) {
        long now = Instant.now().toEpochMilli();
        requestLogs.putIfAbsent(apiKey, new LinkedList<>());

        Deque<Long> timestamps = requestLogs.get(apiKey);

        synchronized (timestamps) {
            while (!timestamps.isEmpty() && (now - timestamps.peekFirst() > Constants.TIME_WINDOW)) {
                timestamps.pollFirst();
            }

            if (timestamps.size() >= Constants.MAX_REQUESTS) {
                throw new CommonException(ErrorCode.RATE_LIMIT_EXCEEDED);
            }

            timestamps.addLast(now);
        }
    }
}
