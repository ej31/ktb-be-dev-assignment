package org.ktb.stocks.configuration;

import lombok.Getter;
import lombok.Setter;
import org.ktb.stocks.service.BucketService;
import org.ktb.stocks.service.MemoryBucketService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties("api.bucket")
public class BucketConfiguration {

    @Bean
    public BucketService bucketService() {
        return new MemoryBucketService(10, 10, Duration.ofSeconds(10), "-v1");
    }

}
