package org.ktb.dev.assignment.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ktb.dev.assignment.core.exception.BusinessException;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
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
public class WebConfig implements WebMvcConfigurer {

    private static final String HEADER_API_KEY = "x-api-key";
    private static final String PARAM_API_KEY = "apikey";

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
                // 헤더에서 API 키 확인
                String headerApiKey = request.getHeader(HEADER_API_KEY);
                // 파라미터에서 API 키 확인
                String paramApiKey = request.getParameter(PARAM_API_KEY);

                // 둘 다 없는 경우
                if (headerApiKey == null && paramApiKey == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    throw new BusinessException(CustomErrorCode.MISSING_API_KEY);
                }

                // 키 검증
                if (headerApiKey != null && headerApiKey.equals(API_KEY)) {
                    return true;
                }

                if (paramApiKey != null && paramApiKey.equals(API_KEY)) {
                    return true;
                }

                // 여기까지 왔다면 키가 불일치
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                throw new BusinessException(CustomErrorCode.INVALID_API_KEY);
            }
        }).addPathPatterns("/api/**");
    }
}