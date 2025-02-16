package org.ktb.ktbbedevassignment.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class JsonXmlResponseAspect {

    private final ObjectMapper jsonMapper;
    private final XmlMapper xmlMapper;

    public JsonXmlResponseAspect(
            @Qualifier("objectMapper") ObjectMapper jsonMapper, // MapperConfig 의 objectMapper 빈을 주입
            XmlMapper xmlMapper
    ) {
        this.jsonMapper = jsonMapper;
        this.xmlMapper = xmlMapper;
    }

    @Around("@annotation(org.ktb.ktbbedevassignment.aop.JsonXmlResponse)")
    public Object handleJsonXmlResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return result;
        }

        HttpServletRequest request = attributes.getRequest();

        if (!(result instanceof ResponseEntity<?> responseEntity)) {
            return result; // ResponseEntity가 아니면 원본 그대로 반환
        }

        HttpStatusCode status = responseEntity.getStatusCode();
        Object responseBody = responseEntity.getBody();

        String format = request.getParameter("format") != null ? request.getParameter("format") : "json";

        if (format.equalsIgnoreCase("xml")) {
            return ResponseEntity.status(status)
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xmlMapper.writeValueAsString(responseBody));
        }

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonMapper.writeValueAsString(responseBody));
    }
}
