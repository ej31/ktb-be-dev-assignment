package com.ktb.assignment.interceptor.pre;

import com.ktb.assignment.exception.CommonException;
import com.ktb.assignment.exception.ErrorCode;
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
        if (apiKey == null || apiKey.isEmpty()) throw new CommonException(ErrorCode.MISSING_API_KEY_PARAMETER);

        // API Key 검증
        if (!API_KEY.equals(apiKey)) throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);


        return true;
    }
}
