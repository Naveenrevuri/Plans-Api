package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final HeaderAuthenticationFilter headerAuthFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(HeaderAuthenticationFilter headerAuthFilter,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.headerAuthFilter = headerAuthFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(customAccessDeniedHandler)   // 🔥 403 handle here
            )
            .addFilterBefore(
                headerAuthFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
