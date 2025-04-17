package ru.fmd.todo_service.todo_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(managerRequestMatchers -> managerRequestMatchers
                        .requestMatchers("/api/v1/task/login/**").permitAll()
                        .requestMatchers("/api/v1/task/register/**").permitAll()
                        .requestMatchers("/api/v1/task/user/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
