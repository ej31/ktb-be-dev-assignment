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
import org.ktb.ktbbedevassignment.util.RateLimiter;
import static org.ktb.ktbbedevassignment.constant.ApiConstant.API_KEY_HEADER;
import static org.ktb.ktbbedevassignment.constant.ApiConstant.API_KEY_PARAM;

public class RateLimiterFilter implements Filter {

    private final RateLimiter rateLimiter;

    public RateLimiterFilter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String apiKey = Optional.ofNullable(httpRequest.getHeader(API_KEY_HEADER))
                .orElse(httpRequest.getParameter(API_KEY_PARAM));

        if (!rateLimiter.allowRequest(apiKey)) {
            httpResponse.sendError(429,
                    "요청이 너무 많습니다. 10초 내 최대 10건의 요청만 허용됩니다. 잠시 후 다시 시도해주세요.");
            return;
        }

        filterChain.doFilter(httpRequest, httpResponse);
    }
}
