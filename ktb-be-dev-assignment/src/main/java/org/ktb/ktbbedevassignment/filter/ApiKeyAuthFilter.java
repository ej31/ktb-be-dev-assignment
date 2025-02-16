package org.ktb.ktbbedevassignment.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.ktb.ktbbedevassignment.application.ApiKeyValidator;
import static org.ktb.ktbbedevassignment.constant.ApiConstant.API_KEY_HEADER;
import static org.ktb.ktbbedevassignment.constant.ApiConstant.API_KEY_PARAM;

public class ApiKeyAuthFilter implements Filter {

    private ApiKeyValidator apiKeyValidator;

    public ApiKeyAuthFilter(ApiKeyValidator apiKeyValidator) {
        this.apiKeyValidator = apiKeyValidator;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String apiKey = Optional.ofNullable(httpRequest.getHeader(API_KEY_HEADER))
                .orElse(httpRequest.getParameter(API_KEY_PARAM));

        apiKeyValidator.validateApiKey(apiKey);

        filterChain.doFilter(httpRequest, httpResponse);
    }
}
