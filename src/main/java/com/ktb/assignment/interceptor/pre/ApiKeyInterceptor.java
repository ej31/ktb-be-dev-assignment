package com.ktb.assignment.interceptor.pre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${spring.api.key}")
    private String API_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("x-api-key");

        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = request.getParameter("apikey");
        }

        // API Key가 없는 경우
        if (apiKey == null || apiKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "API key가 없습니다.");
            return false;
        }

        // API Key 검증
        if (!API_KEY.equals(apiKey)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "API key가 올바르지 않습니다.");
            return false;
        }

        return true;
    }
}
