package org.ktb.ktbbedevassignment.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.ktb.ktbbedevassignment.config.MapperConfig;
import org.ktb.ktbbedevassignment.dto.ApiResponse;
import org.ktb.ktbbedevassignment.exception.InvalidApiKeyException;
import org.ktb.ktbbedevassignment.exception.NotMatchedApiKeyException;
import org.ktb.ktbbedevassignment.exception.RateLimitExceededException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExceptionHandlingFilterTest {

    private ExceptionHandlingFilter filter;
    private ObjectMapper objectMapper;
    private XmlMapper xmlMapper;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    private StringWriter responseWriter;

    @BeforeAll
    void beforeAll() {
        MapperConfig mapperConfig = new MapperConfig();
        objectMapper = mapperConfig.objectMapper();
        xmlMapper = mapperConfig.xmlMapper();
        filter = new ExceptionHandlingFilter(objectMapper, xmlMapper);
    }

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    @DisplayName("정상 요청 시 필터가 예외 없이 doFilter 진행")
    void givenValidRequest_whenFiltering_thenProceedWithoutError() throws IOException, ServletException {
        filter.doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);
    }

    private void verifyErrorResponse(HttpStatus expectedStatus, String expectedMessage) throws IOException {
        verify(response).setStatus(expectedStatus.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<?> apiResponse = objectMapper.readValue(responseWriter.toString(), ApiResponse.class);
        assertThat(apiResponse.status()).isEqualTo(expectedStatus.value());
        assertThat(apiResponse.message()).isEqualTo(expectedMessage);
    }

    @Nested
    @DisplayName("API Key 예외 처리 테스트")
    class ApiKeyExceptionTests {

        @Test
        @DisplayName("InvalidApiKeyException 발생 시 400 JSON 응답을 반환한다.")
        void givenInvalidApiKeyException_whenFiltering_thenReturnsBadRequestJson() throws IOException, ServletException {
            doThrow(new InvalidApiKeyException()).when(chain).doFilter(request, response);

            filter.doFilter(request, response, chain);

            verifyErrorResponse(HttpStatus.BAD_REQUEST, "API Key가 존재하지 않습니다.");
        }

        @Test
        @DisplayName("NotMatchedApiKeyException 발생 시 403 JSON 응답을 반환한다.")
        void givenNotMatchedApiKeyException_whenFiltering_thenReturnsForbiddenJson() throws IOException, ServletException {
            doThrow(new NotMatchedApiKeyException()).when(chain).doFilter(request, response);

            filter.doFilter(request, response, chain);

            verifyErrorResponse(HttpStatus.FORBIDDEN, "잘못된 API Key입니다.");
        }
    }

    @Nested
    @DisplayName("Rate Limit 초과 테스트")
    class RateLimitExceededTests {

        @Test
        @DisplayName("RateLimitExceededException 발생 시 429 JSON 응답을 반환한다.")
        void givenRateLimitExceededException_whenFiltering_thenReturnsTooManyRequestsJson() throws IOException, ServletException {
            doThrow(new RateLimitExceededException()).when(chain).doFilter(request, response);

            filter.doFilter(request, response, chain);

            verifyErrorResponse(HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다. 10초 내 최대 10건의 요청만 허용됩니다. 잠시 후 다시 시도해주세요.");
        }
    }

    @Nested
    @DisplayName("JSON vs XML 응답 테스트")
    class JsonXmlResponseTests {

        @Test
        @DisplayName("JSON 응답이 정상적으로 반환된다.")
        void givenException_whenFiltering_thenReturnsJsonResponse() throws IOException, ServletException {
            when(request.getParameter("format")).thenReturn("json");
            doThrow(new InvalidApiKeyException()).when(chain).doFilter(request, response);

            filter.doFilter(request, response, chain);

            verifyErrorResponse(HttpStatus.BAD_REQUEST, "API Key가 존재하지 않습니다.");
        }

        @Test
        @DisplayName("XML 응답이 정상적으로 반환된다.")
        void givenException_whenFiltering_thenReturnsXmlResponse() throws IOException, ServletException {
            when(request.getParameter("format")).thenReturn("xml");
            doThrow(new InvalidApiKeyException()).when(chain).doFilter(request, response);

            filter.doFilter(request, response, chain);

            verify(response).setContentType(MediaType.APPLICATION_XML_VALUE);
            String xmlResponse = responseWriter.toString();
            assertThat(xmlResponse).contains("<status>400</status>");
            assertThat(xmlResponse).contains("<message>API Key가 존재하지 않습니다.</message>");
        }
    }

    @Nested
    @DisplayName("기타 예외 처리 테스트")
    class GeneralExceptionTests {

        @Test
        @DisplayName("일반적인 예외 발생 시 500 응답을 반환한다.")
        void givenGeneralException_whenFiltering_thenReturnsInternalServerError() throws IOException, ServletException {
            doThrow(new RuntimeException("Unexpected error")).when(chain).doFilter(request, response);

            filter.doFilter(request, response, chain);

            verifyErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
        }
    }
}
