package org.ktb.ktbbedevassignment.util;

import java.time.Clock;
import java.time.Instant;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.ktb.ktbbedevassignment.fixture.RateLimiterTestFixture.TEST_FIXED_TIME_INSTANT;
import static org.ktb.ktbbedevassignment.fixture.RateLimiterTestFixture.TEST_MAX_REQUESTS;
import static org.ktb.ktbbedevassignment.fixture.RateLimiterTestFixture.TEST_TIME_WINDOW_MILLIS;
import static org.ktb.ktbbedevassignment.fixture.RateLimiterTestFixture.generateRandomClientId;
import static org.ktb.ktbbedevassignment.fixture.RateLimiterTestFixture.plusMillis;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RateLimiterTest {

    private Clock clock;
    private RateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        Instant fixedInstant = TEST_FIXED_TIME_INSTANT;
        clock = mock(Clock.class);
        when(clock.millis()).thenReturn(fixedInstant.toEpochMilli());

        rateLimiter = new RateLimiter(TEST_MAX_REQUESTS, TEST_TIME_WINDOW_MILLIS, clock);
    }

    @Test
    @DisplayName("요청이 허용된 한도 내에 있으면 정상적으로 처리된다")
    void givenRequestsWithinLimit_whenCheckingAllowance_thenAllRequestsAreAllowed() {
        String clientId = generateRandomClientId();

        IntStream.range(0, TEST_MAX_REQUESTS).forEach(i -> assertThat(rateLimiter.allowRequest(clientId)).isTrue());
    }

    @Test
    @DisplayName("요청이 허용된 한도를 초과하면 추가 요청이 차단된다")
    void givenRequestsExceedingLimit_whenCheckingAllowance_thenExcessRequestsAreBlocked() {
        String clientId = generateRandomClientId();

        IntStream.range(0, TEST_MAX_REQUESTS).forEach(i -> assertThat(rateLimiter.allowRequest(clientId)).isTrue());

        assertThat(rateLimiter.allowRequest(clientId)).isFalse();
    }

    @Test
    @DisplayName("요청 제한 시간이 지나면 다시 요청이 허용된다")
    void givenTimeWindowExpires_whenCheckingAllowance_thenRequestsAreAllowedAgain() {
        String clientId = generateRandomClientId();

        IntStream.range(0, TEST_MAX_REQUESTS).forEach(i -> assertThat(rateLimiter.allowRequest(clientId)).isTrue());
        assertThat(rateLimiter.allowRequest(clientId)).isFalse();

        Instant plusMillis = plusMillis(TEST_FIXED_TIME_INSTANT, TEST_TIME_WINDOW_MILLIS + 1);
        when(clock.millis()).thenReturn(plusMillis.toEpochMilli());

        assertThat(rateLimiter.allowRequest(clientId)).isTrue();
    }

    @Test
    @DisplayName("서로 다른 클라이언트는 독립적으로 요청 제한이 적용된다")
    void givenMultipleClients_whenCheckingAllowance_thenRateLimitIsAppliedIndependently() {
        String client1 = generateRandomClientId();
        String client2 = generateRandomClientId();

        IntStream.range(0, TEST_MAX_REQUESTS).forEach(i -> assertThat(rateLimiter.allowRequest(client1)).isTrue());
        assertThat(rateLimiter.allowRequest(client1)).isFalse();

        IntStream.range(0, TEST_MAX_REQUESTS).forEach(i -> assertThat(rateLimiter.allowRequest(client2)).isTrue());
        assertThat(rateLimiter.allowRequest(client2)).isFalse();
    }
}
