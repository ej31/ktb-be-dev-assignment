package com.judycompany.assignment1.v1.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class QuotaService {
    private static final int MAX_REQUESTS = 10;
    private static final long TIME_WINDOW_MS = TimeUnit.SECONDS.toMillis(10);

    private final ConcurrentHashMap<String, RequestQuota> quotaMap = new ConcurrentHashMap<>();

    public boolean isAllowed(String apiKey) {
        long currentTime = System.currentTimeMillis();
        RequestQuota quota = quotaMap.computeIfAbsent(apiKey, k -> new RequestQuota());

        synchronized (quota) {
            if (currentTime - quota.timestamp > TIME_WINDOW_MS) {
                quota.timestamp = currentTime;
                quota.requests = 0;
            }

            if (quota.requests < MAX_REQUESTS) {
                quota.requests++;
                return true;
            } else {
                return false;
            }
        }
    }

    private static class RequestQuota {
        long timestamp;
        int requests;
    }
}
