package kcs.kcsdev.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

      @Override
      public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer
                    .favorParameter(true)
                    .parameterName("format")
                    .ignoreAcceptHeader(true)
                    .defaultContentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .mediaType("json", org.springframework.http.MediaType.APPLICATION_JSON)
                    .mediaType("xml", org.springframework.http.MediaType.APPLICATION_XML);
      }
}
