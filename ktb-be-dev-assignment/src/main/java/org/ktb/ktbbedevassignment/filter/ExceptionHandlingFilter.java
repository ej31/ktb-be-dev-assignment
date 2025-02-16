package org.ktb.ktbbedevassignment.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.ktb.ktbbedevassignment.dto.ApiResponse;
import org.ktb.ktbbedevassignment.exception.InvalidApiKeyException;
import org.ktb.ktbbedevassignment.exception.NotMatchedApiKeyException;
import org.ktb.ktbbedevassignment.exception.RateLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ExceptionHandlingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingFilter.class);

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public ExceptionHandlingFilter(
            @Qualifier("objectMapper") ObjectMapper objectMapper, // MapperConfig 의 objectMapper 빈을 주입
            XmlMapper xmlMapper
    ) {
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (InvalidApiKeyException | NotMatchedApiKeyException e) {
            logger.warn("API Key 검증 실패: {}", e.getMessage(), e);

            sendErrorResponse(
                    (HttpServletRequest) request,
                    (HttpServletResponse) response,
                    HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getReason()
            );
        } catch (RateLimitExceededException e) {
            logger.warn("Rate Limiter 발동: {}", e.getMessage(), e);

            sendErrorResponse(
                    (HttpServletRequest) request,
                    (HttpServletResponse) response,
                    HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getReason()
            );
        } catch (Exception e) {
            logger.error("필터에서 처리되지 않은 예외 발생: {}", e.getMessage(), e);

            sendErrorResponse(
                    (HttpServletRequest) request,
                    (HttpServletResponse) response,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "서버 내부 오류가 발생했습니다."
            );
        }
    }

    private void sendErrorResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpStatus status,
            String message
    ) throws IOException {
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");

        String format = request.getParameter("format") != null ? request.getParameter("format") : "json";

        if ("xml".equals(format)) {
            response.setContentType(MediaType.APPLICATION_XML_VALUE);
            xmlMapper.writeValue(response.getWriter(), ApiResponse.error(status, message));
            return;
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Void> apiResponse = ApiResponse.error(status, message);
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
