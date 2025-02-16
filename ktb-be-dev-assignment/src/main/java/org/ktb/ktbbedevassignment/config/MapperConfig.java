package org.ktb.ktbbedevassignment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule()); // LocalDateTime 직렬화를 위한 모듈 등록
    }

    @Bean
    public XmlMapper xmlMapper() {
        return (XmlMapper) new XmlMapper()
                .registerModule(new JavaTimeModule());
    }
}
