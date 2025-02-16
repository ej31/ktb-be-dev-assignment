package com.example.stock.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor{
    @Value("${API_KEY}")
    private String apikey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("API Key: " + apikey);
        String headerApiKey = request.getHeader("x-api-key");
        String paramApiKey = request.getParameter("apikey");

        if(apikey.equals(headerApiKey) || apikey.equals(paramApiKey)) {
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);   // 403
            response.getWriter().write("Invalid Api Key");
            return false;
        }
    }
}