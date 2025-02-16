package org.ktb.ktbbedevassignment.fixture;

import java.time.Instant;

public class RateLimiterTestFixture {

    public static final int TEST_MAX_REQUESTS = 500;

    public static final long TEST_TIME_WINDOW_MILLIS = 2000;

    public static final Instant TEST_FIXED_TIME_INSTANT = Instant.parse("2025-02-16T12:00:00Z");

    public static String generateRandomClientId() {
        return "client-" + (int) (Math.random() * 1000);
    }

    public static Instant plusMillis(Instant instant, long millis) {
        return instant.plusMillis(millis);
    }
}
