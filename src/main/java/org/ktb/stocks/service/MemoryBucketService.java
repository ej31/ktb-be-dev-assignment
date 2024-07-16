package org.ktb.stocks.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MemoryBucketService implements BucketService {
    private final Map<String, Bucket> cache = new HashMap<>();
    private final Integer capacity;
    private final Integer refillTokens;
    private final Duration refillPeriod;
    private final String apiKeySuffix;


    @Override
    public Bucket resolveBucket(String apiKey) {
        if (cache.containsKey(apiKey)) {
            return cache.get(apiKey);
        }

        Bucket bucket = createBucket(apiKey);
        cache.put(apiKey, bucket);
        return bucket;
    }

    private Bucket createBucket(String apiKey) {
        Bandwidth bandwidth = Bandwidth.builder()
                .capacity(capacity)
                .refillIntervally(refillTokens, refillPeriod)
                .id(apiKey + apiKeySuffix)
                .build();

        return Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }
}
