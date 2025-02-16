package org.ktb.ktbbedevassignment.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ktb.ktbbedevassignment.util.RateLimiter;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        filter = new RateLimiterFilter(rateLimiter);
        responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);
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
        @DisplayName("요청이 너무 많으면 429 응답을 반환한다.")
        void givenRateLimitExceeded_whenFiltering_thenReturnsTooManyRequests() throws IOException, ServletException {
            when(request.getHeader("x-api-key")).thenReturn(TEST_API_KEY);
            when(rateLimiter.allowRequest(TEST_API_KEY)).thenReturn(false);

            filter.doFilter(request, response, chain);

            verify(rateLimiter).allowRequest(TEST_API_KEY);
            verify(response).sendError(429, "요청이 너무 많습니다. 10초 내 최대 10건의 요청만 허용됩니다. 잠시 후 다시 시도해주세요.");
            verify(chain, never()).doFilter(any(), any());
        }
    }

    @Nested
    @DisplayName("API Key 없이 요청 시")
    class MissingApiKey {

        @Test
        @DisplayName("API Key가 없으면 요청을 차단하고 429 응답을 반환한다.")
        void givenNoApiKey_whenFiltering_thenReturnsTooManyRequests() throws IOException, ServletException {
            when(request.getHeader("x-api-key")).thenReturn(null);
            when(request.getParameter("apikey")).thenReturn(null);
            when(rateLimiter.allowRequest(null)).thenReturn(false);

            filter.doFilter(request, response, chain);

            verify(rateLimiter).allowRequest(null);
            verify(response).sendError(429, "요청이 너무 많습니다. 10초 내 최대 10건의 요청만 허용됩니다. 잠시 후 다시 시도해주세요.");
            verify(chain, never()).doFilter(any(), any());
        }
    }
}
