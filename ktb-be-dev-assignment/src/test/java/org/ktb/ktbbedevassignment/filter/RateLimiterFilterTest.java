package org.ktb.ktbbedevassignment.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ktb.ktbbedevassignment.exception.RateLimitExceededException;
import org.ktb.ktbbedevassignment.util.RateLimiter;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ktb.ktbbedevassignment.fixture.ApiKeyTestFixture.TEST_API_KEY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RateLimiterFilterTest {

    private RateLimiterFilter filter;

    @Mock
    private RateLimiter rateLimiter;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        filter = new RateLimiterFilter(rateLimiter);
    }

    @Nested
    @DisplayName("정상 요청 테스트")
    class AllowedRequests {

        @Test
        @DisplayName("요청이 제한 내에서 허용되면 필터 체인이 계속 실행된다.")
        void givenAllowedRequest_whenFiltering_thenProceedWithoutError() throws IOException, ServletException {
            when(request.getHeader("x-api-key")).thenReturn(TEST_API_KEY);
            when(rateLimiter.allowRequest(TEST_API_KEY)).thenReturn(true);

            filter.doFilter(request, response, chain);

            verify(rateLimiter).allowRequest(TEST_API_KEY);
            verify(chain, times(1)).doFilter(request, response);
            verify(response, never()).sendError(anyInt(), anyString());
        }
    }

    @Nested
    @DisplayName("Rate Limit 초과 테스트")
    class RateLimitExceeded {

        @Test
        @DisplayName("요청이 너무 많으면 Rate Limit 초과 예외를 던지고 필터 체인을 실행하지 않는다.")
        void givenRateLimitExceeded_whenFiltering_thenReturnsTooManyRequests() throws IOException, ServletException {
            when(request.getHeader("x-api-key")).thenReturn(TEST_API_KEY);
            when(rateLimiter.allowRequest(TEST_API_KEY)).thenReturn(false);

            assertThatThrownBy(() -> filter.doFilter(request, response, chain))
                    .isInstanceOf(RateLimitExceededException.class);

            verify(rateLimiter).allowRequest(TEST_API_KEY);
            verify(chain, never()).doFilter(any(), any());
        }
    }

    @Nested
    @DisplayName("API Key 없이 요청 시")
    class MissingApiKey {

        @Test
        @DisplayName("API Key가 없으면 요청을 차단하고 Rate Limit 초과 예외를 던진다.")
        void givenNoApiKey_whenFiltering_thenReturnsTooManyRequests() throws IOException, ServletException {
            when(request.getHeader("x-api-key")).thenReturn(null);
            when(request.getParameter("apikey")).thenReturn(null);
            when(rateLimiter.allowRequest(null)).thenReturn(false);

            assertThatThrownBy(() -> filter.doFilter(request, response, chain))
                    .isInstanceOf(RateLimitExceededException.class);

            verify(rateLimiter).allowRequest(null);
            verify(chain, never()).doFilter(any(), any());
        }
    }
}
