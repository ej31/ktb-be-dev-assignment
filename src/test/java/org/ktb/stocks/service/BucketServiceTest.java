package org.ktb.stocks.service;

import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Test;
import org.ktb.stocks.configuration.BucketConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ContextConfiguration(classes = BucketConfiguration.class)
@SpringBootTest
class BucketServiceTest {

    @Autowired
    BucketService bucketService;


    @Test
    void testBucketService() {
        String bucketName1 = "bucket1";
        String bucketName2 = "bucket2";

        Bucket bucket1 = bucketService.resolveBucket(bucketName1);
        Bucket bucket2 = bucketService.resolveBucket(bucketName2);

        bucket1.tryConsumeAsMuchAsPossible();
        assertFalse(bucket1.tryConsume(1));
        assertTrue(bucket2.tryConsume(1));
    }

}