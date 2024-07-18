package kcs.kcsdev.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import kcs.kcsdev.global.exception.ErrorResponse;
import kcs.kcsdev.global.exception.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationEntryPoint implements AuthenticationEntryPoint {

      private final ObjectMapper objectMapper;

      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response,
              AuthenticationException authException) throws IOException, ServletException {
            Object exception = request.getAttribute("exception");
            log.error("Authentication error: {}", authException.getMessage());
            log.error("Exception attribute: {}", exception);

            if (exception instanceof ErrorCode) {
                  setResponse(response, (ErrorCode) exception);
            } else {
                  setResponse(response, ErrorCode.INVALID_REQUEST);
            }
      }

      private void setResponse(HttpServletResponse response, ErrorCode errorCode)
              throws IOException {
            Map<String, Object> map = new HashMap<>();

            ErrorResponse errorResponse = new ErrorResponse(errorCode, errorCode.getHttpStatus(),
                    errorCode.getDescription());

            map.put("errorCode", errorResponse.getErrorCode().name());
            map.put("errorMessage", errorResponse.getErrorCode().getDescription());

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(errorResponse.getHttpStatus().value());

            log.error("API key error -> {}", errorCode);

            response.getWriter().println(objectMapper.writeValueAsString(map));
      }
}
