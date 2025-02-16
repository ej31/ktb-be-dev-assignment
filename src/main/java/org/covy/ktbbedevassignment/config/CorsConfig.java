package org.covy.ktbbedevassignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 📌 모든 API 경로 허용
                        .allowedOrigins("http://localhost:3000") // 📌 프론트엔드 주소 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 📌 허용할 HTTP 메서드
                        .allowedHeaders("*") // 📌 모든 헤더 허용
                        .allowCredentials(true); // 📌 인증 정보 포함 허용
            }
        };
    }
}

