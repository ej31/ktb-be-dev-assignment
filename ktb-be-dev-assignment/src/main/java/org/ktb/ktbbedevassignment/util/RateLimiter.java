package org.ktb.ktbbedevassignment.util;

import java.time.Clock;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RateLimiter {
    private final int maxRequests;
    private final long timeWindowMillis;
    private final ConcurrentMap<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();
    private final Clock clock;

    public RateLimiter(int maxRequests, long timeWindowMillis, Clock clock) {
        this.maxRequests = maxRequests;
        this.timeWindowMillis = timeWindowMillis;
        this.clock = clock;
    }

    public boolean allowRequest(String clientId) {
        long now = clock.millis();
        requestLogs.putIfAbsent(clientId, new LinkedList<>());

        Deque<Long> timestamps = requestLogs.get(clientId);

        synchronized (timestamps) {
            while (!timestamps.isEmpty() && now - timestamps.peekFirst() > timeWindowMillis) {
                timestamps.pollFirst();
            }

            if (timestamps.size() >= maxRequests) {
                return false;
            }

            timestamps.addLast(now);
            return true;
        }
    }
}
