package org.example.stockapitest.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ThrottleService {

    private final int LIMIT = 10; // 10 requests
    private final int TIME_WINDOW = 10; // 10 seconds

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String apiKey) {
        Bucket bucket = buckets.computeIfAbsent(apiKey, this::newBucket);
        return bucket.tryConsume(1);
    }

    private Bucket newBucket(String apiKey) {
        Refill refill = Refill.greedy(LIMIT, Duration.ofSeconds(TIME_WINDOW));
        Bandwidth limit = Bandwidth.classic(LIMIT, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
