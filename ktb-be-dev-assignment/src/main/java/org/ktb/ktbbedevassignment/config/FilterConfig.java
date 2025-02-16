package org.ktb.ktbbedevassignment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.ktb.ktbbedevassignment.application.ApiKeyValidator;
import org.ktb.ktbbedevassignment.filter.ApiKeyAuthFilter;
import org.ktb.ktbbedevassignment.filter.ExceptionHandlingFilter;
import org.ktb.ktbbedevassignment.filter.RateLimiterFilter;
import org.ktb.ktbbedevassignment.util.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final ApiKeyValidator apiKeyValidator;
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public FilterConfig(
            ApiKeyValidator apiKeyValidator,
            @Qualifier("objectMapper") ObjectMapper objectMapper, // MapperConfig 의 objectMapper 빈을 주입
            XmlMapper xmlMapper
    ) {
        this.apiKeyValidator = apiKeyValidator;
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
    }

    @Bean
    public FilterRegistrationBean<ExceptionHandlingFilter> exceptionHandlingFilter() {
        FilterRegistrationBean<ExceptionHandlingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ExceptionHandlingFilter(objectMapper, xmlMapper));
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyAuthFilter() {
        FilterRegistrationBean<ApiKeyAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiKeyAuthFilter(apiKeyValidator));
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RateLimiterFilter> rateLimiterFilter(RateLimiter rateLimiter) {
        FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimiterFilter(rateLimiter));
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
