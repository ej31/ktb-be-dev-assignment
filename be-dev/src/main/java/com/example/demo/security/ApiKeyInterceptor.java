package com.example.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * API Key 인증 인터셉터
 * - 모든 API 요청에 대해 유효한 API Key가 포함되었는지 검증
 * - API Key는 `application.properties`에서 설정 가능
 */
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${api.key}") // application.properties에서 API 키 가져오기
    private String validApiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("x-api-key"); // 헤더에서 API 키 가져오기
        if (apiKey == null) {
            apiKey = request.getParameter("apikey"); // 쿼리 파라미터에서도 가져오기
        }

        if (apiKey == null) {
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "API Key가 필요합니다.");
            return false;
        }

        if (!apiKey.equals(validApiKey)) {  // API 키가 일치하지 않으면 403 반환
            sendJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, "잘못된 API Key입니다.");
            return false;
        }

        return true; // API Key 검증 통과
    }

    // JSON 응답을 반환
    private boolean sendJsonResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(String.format("{\"message\": \"%s\", \"status\": %d}", message, status));
        return false;  // API 요청 차단 (preHandle 종료)
    }
}