package com.JonathanGhaly.travel.poi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Anyone authenticated can read POIs
                        .requestMatchers(HttpMethod.GET, "/api/v1/pois/**", "/api/v1/tags/**").authenticated()
                        // Only Admins can modify POIs
                        .requestMatchers(HttpMethod.POST, "/api/v1/pois/**", "/api/v1/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/pois/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/pois/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/pois/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthConverter()))
                );

        return http.build();
    }
}