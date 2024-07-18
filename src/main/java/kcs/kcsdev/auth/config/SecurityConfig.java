package kcs.kcsdev.auth.config;

import kcs.kcsdev.auth.security.ApiKeyAccessDeniedHandler;
import kcs.kcsdev.auth.security.ApiKeyAuthFilter;
import kcs.kcsdev.auth.security.ApiKeyAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

      private final ApiKeyAuthFilter apiKeyAuthFilter;
      private final ApiKeyAuthenticationEntryPoint apiKeyAuthenticationEntryPoint;
      private final ApiKeyAccessDeniedHandler apiKeyAccessDeniedHandler;

      @Bean
      public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .formLogin(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .sessionManagement((sessionManagement) ->
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(
                            auth -> auth
                                    .requestMatchers(HttpMethod.GET, "/api/v1/stock/**")
                                    .authenticated()
                                    .anyRequest().permitAll()
                    ).exceptionHandling(configurer -> {
                          configurer.authenticationEntryPoint(apiKeyAuthenticationEntryPoint);
                          configurer.accessDeniedHandler(apiKeyAccessDeniedHandler);
                    })
                    .addFilterBefore(apiKeyAuthFilter,
                            UsernamePasswordAuthenticationFilter.class);

            return httpSecurity.build();
      }
}
