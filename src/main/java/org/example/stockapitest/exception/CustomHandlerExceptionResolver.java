package org.example.stockapitest.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.stockapitest.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.io.IOException;

public class CustomHandlerExceptionResolver extends DefaultHandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(CustomHandlerExceptionResolver.class);

    @Override
    protected ModelAndView handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        CommonResponse<?> commonResponse = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "API endpoint not found", null);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(commonResponse));

        return new ModelAndView();
    }
}
