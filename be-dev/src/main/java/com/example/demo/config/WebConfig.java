package com.example.demo.config;

import com.example.demo.security.ApiKeyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  WebMvc 설정 클래스
 * - API Key 인증을 위해 `ApiKeyInterceptor`를 등록
 * - 특정 경로(`/api/**`)에 인터셉터를 적용하여 보안 강화
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiKeyInterceptor apiKeyInterceptor;

    @Autowired
    public WebConfig(ApiKeyInterceptor apiKeyInterceptor) {
        this.apiKeyInterceptor = apiKeyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/api/**");  // 모든 `/api/**` 경로에 API Key 인증 적용
    }
}