package org.ktb.stock.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BucketConfig {
    // API 키별 버킷 저장소
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // 버킷 생성 설정
    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(10))))
                .build();
    }

    // API 키에 대한 버킷 가져오기
    public Bucket resolveBucket(String apiKey) {
        return buckets.computeIfAbsent(apiKey, k -> newBucket());
    }
}
