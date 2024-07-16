package org.ktb.stocks.service;

import io.github.bucket4j.Bucket;

public interface BucketService {
    Bucket resolveBucket(String apiKey);
}
