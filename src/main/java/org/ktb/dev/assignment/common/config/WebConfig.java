package org.ktb.dev.assignment.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.BusinessException;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.core.ratelimit.ApiKeyRateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/*
 * !!할일 : 프로트엔드 포트에 맞게 CORS 설정 수정 필요 !
 * author : hyuk.kim (김상혁)
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private static final String HEADER_API_KEY = "x-api-key";
    private static final String PARAM_API_KEY = "apikey";
    private final ApiKeyRateLimiter rateLimiter;

    @Value("${api.key.secret}")
    private String API_KEY;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                String headerApiKey = request.getHeader(HEADER_API_KEY);
                String paramApiKey = request.getParameter(PARAM_API_KEY);

                if (headerApiKey == null && paramApiKey == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    throw new BusinessException(CustomErrorCode.MISSING_API_KEY);
                }

                // API 키를 request attribute로 저장 (Rate Limiting에서 사용하는 값!)
                String validApiKey = headerApiKey != null ? headerApiKey : paramApiKey;
                if (validApiKey.equals(API_KEY)) {
                    request.setAttribute("VALID_API_KEY", validApiKey);
                    return true;
                }

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                throw new BusinessException(CustomErrorCode.INVALID_API_KEY);
            }
        }).addPathPatterns("/api/**").order(1); // 1. API 키 검증 인터셉터 (order: 1)

        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                String apiKey = (String) request.getAttribute("VALID_API_KEY");

                // API 키가 이미 검증되었으므로, null 체크는 불필요!
                if (!rateLimiter.tryAcquire(apiKey)) {
                    throw new BusinessException(
                            CustomErrorCode.RATE_LIMIT_EXCEEDED,
                            ApiKeyRateLimiter.MAX_REQUESTS,
                            ApiKeyRateLimiter.WINDOW_SECONDS
                    );
                }

                return true;
            }
        }).addPathPatterns("/api/**").order(2);  // 2. Rate Limiting 인터셉터 (order: 2)
    }
}