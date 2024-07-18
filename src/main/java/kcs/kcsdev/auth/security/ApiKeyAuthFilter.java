package kcs.kcsdev.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

      private static final String API_KEY_HEADER = "x-api-key";
      private static final String API_KEY_PARAM = "apikey";
      private final RateLimiter rateLimiter;
      @Value("${security.api-key}")
      private String apiKey;

      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
              FilterChain filterChain)
              throws ServletException, IOException {
            String requestApiKey = request.getHeader(API_KEY_HEADER);
            log.info("Request API Key from header: {}", requestApiKey);

            if (requestApiKey == null) {
                  requestApiKey = request.getParameter(API_KEY_PARAM);
                  log.info("Request API Key from parameter: {}", requestApiKey);
            }

            if (requestApiKey == null) {
                  log.error("API key is missing");
                  response.setContentType("application/json;charset=UTF-8");
                  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                  response.getWriter()
                          .write("{\"error\": \"Bad Request\", \"message\": \"API key is missing\"}");
                  return;
            }

            if (!apiKey.equals(requestApiKey)) {
                  log.error("Invalid API key");
                  response.setContentType("application/json;charset=UTF-8");
                  response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                  response.getWriter()
                          .write("{\"error\": \"Forbidden\", \"message\": \"Invalid API key\"}");
                  return;
            }

            if (!rateLimiter.isRequestAllowed(requestApiKey)) {
                  log.error("Too many requests for API key: {}", requestApiKey);
                  response.setContentType("application/json;charset=UTF-8");
                  response.setStatus(429); // 직접 429 상태 코드를 사용
                  response.getWriter()
                          .write("{\"error\": \"Too Many Requests\", \"message\": \"You have exceeded the request limit.\"}");
                  return;
            }

            //인증 정보
            UserDetails userDetails = User.withUsername("apiKeyUser").password("").authorities(
                    Collections.emptyList()).build();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("API key is valid");
            filterChain.doFilter(request, response);
      }
}
