package org.example.stockapitest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.stockapitest.exception.ApiKeyException;
import org.example.stockapitest.response.CommonResponse;
import org.example.stockapitest.service.ThrottleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {
    private static final String API_KEY_HEADER = "x-api-key";
    private static final String API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    @Autowired
    private ThrottleService throttleService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try {
            String apiKey = httpRequest.getHeader(API_KEY_HEADER); //요청 헤더에서 api키 가져오기

            if (!throttleService.isRequestAllowed(apiKey)) {
                throw new ApiKeyException(429, "Too Many Requests");
            }

            if (apiKey == null) {
                throw new ApiKeyException(HttpServletResponse.SC_BAD_REQUEST, "No API key"); //
            }

            if (!API_KEY.equals(apiKey)) {
                throw new ApiKeyException(HttpServletResponse.SC_FORBIDDEN, "Invalid API key");
            }

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (ApiKeyException e) {
            httpResponse.setStatus(e.getStatus());
            httpResponse.setContentType("application/json");
            CommonResponse<?> errorResponse = new CommonResponse<>(e.getStatus(), e.getMessage(), null); //commonresponse 사용해서 응답
            httpResponse.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse)); //응답 바디에 errorResponse 넣기
        }
    }

}
