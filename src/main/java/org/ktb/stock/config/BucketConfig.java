package org.ktb.stock.config;

import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BucketConfig {
    // API 키별 버킷 저장소
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // 버킷 생성 설정
    public Bucket createBucket() {
        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(10)
                        .refillIntervally(10, Duration.ofSeconds(10)))
                .build();
    }

    // API 키에 대한 버킷 가져오기
    public Bucket resolveBucket(String apiKey) {
        return buckets.computeIfAbsent(apiKey, k -> createBucket());
    }
}
