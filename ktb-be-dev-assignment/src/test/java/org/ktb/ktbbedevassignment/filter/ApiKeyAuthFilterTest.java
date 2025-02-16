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
import org.ktb.ktbbedevassignment.application.ApiKeyValidator;
import org.ktb.ktbbedevassignment.exception.InvalidApiKeyException;
import org.ktb.ktbbedevassignment.exception.NotMatchedApiKeyException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ktb.ktbbedevassignment.fixture.ApiKeyTestFixture.TEST_API_KEY;
import static org.ktb.ktbbedevassignment.fixture.ApiKeyTestFixture.TEST_INVALID_API_KEY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApiKeyAuthFilterTest {

    private ApiKeyAuthFilter filter;

    @Mock private ApiKeyValidator apiKeyValidator;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new ApiKeyAuthFilter(apiKeyValidator);
    }

    @Nested
    @DisplayName("정상 요청 테스트")
    class ValidRequestTests {

        @Test
        @DisplayName("올바른 API Key 헤더가 있으면 요청이 정상적으로 통과된다.")
        void givenValidApiKeyHeader_whenFiltering_thenProceedWithoutError() throws IOException, ServletException {
            when(request.getHeader("x-api-key")).thenReturn(TEST_API_KEY);

            filter.doFilter(request, response, chain);

            verify(apiKeyValidator).validateApiKey(TEST_API_KEY);
            verify(chain, times(1)).doFilter(request, response);
        }

        @Test
        @DisplayName("올바른 API Key 파라미터가 있으면 요청이 정상적으로 통과된다.")
        void givenValidApiKeyParam_whenFiltering_thenProceedWithoutError() throws IOException, ServletException {
            when(request.getHeader("x-api-key")).thenReturn(null);
            when(request.getParameter("apikey")).thenReturn(TEST_API_KEY);

            filter.doFilter(request, response, chain);

            verify(apiKeyValidator).validateApiKey(TEST_API_KEY);
            verify(chain, times(1)).doFilter(request, response);
        }
    }

    @Nested
    @DisplayName("API Key 예외 발생 테스트")
    class ApiKeyExceptionTests {

        @Test
        @DisplayName("API Key가 없으면 InvalidApiKeyException이 발생해야 한다.")
        void givenMissingApiKey_whenFiltering_thenThrowsInvalidApiKeyException() throws ServletException, IOException {
            when(request.getHeader("x-api-key")).thenReturn(null);
            when(request.getParameter("apikey")).thenReturn(null);
            doThrow(new InvalidApiKeyException()).when(apiKeyValidator).validateApiKey(null);

            assertThatThrownBy(() -> filter.doFilter(request, response, chain))
                    .isInstanceOf(InvalidApiKeyException.class);

            verify(apiKeyValidator).validateApiKey(null);
            verify(chain, never()).doFilter(any(), any());
        }

        @Test
        @DisplayName("잘못된 API Key가 있으면 NotMatchedApiKeyException이 발생해야 한다.")
        void givenInvalidApiKey_whenFiltering_thenThrowsNotMatchedApiKeyException() throws ServletException, IOException {
            when(request.getHeader("x-api-key")).thenReturn(TEST_INVALID_API_KEY);
            doThrow(new NotMatchedApiKeyException()).when(apiKeyValidator).validateApiKey(TEST_INVALID_API_KEY);

            assertThatThrownBy(() -> filter.doFilter(request, response, chain))
                    .isInstanceOf(NotMatchedApiKeyException.class);

            verify(apiKeyValidator).validateApiKey(TEST_INVALID_API_KEY);
            verify(chain, never()).doFilter(any(), any());
        }
    }
}
