package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 *  Spring Security 기본 설정 클래스
 * - API 보안을 간단하게 유지하기 위해 모든 요청을 허용
 * - CSRF 보호 및 기본 인증, 폼 로그인을 비활성화
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 모든 요청 허용 (특정 경로 제한 없음)
            )
            .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (API 서버이므로 필요 없음)
            .httpBasic(httpBasic -> httpBasic.disable()) // HTTP 기본 인증 비활성화
            .formLogin(formLogin -> formLogin.disable()); // 폼 로그인 활성화

        return http.build();
    }
}