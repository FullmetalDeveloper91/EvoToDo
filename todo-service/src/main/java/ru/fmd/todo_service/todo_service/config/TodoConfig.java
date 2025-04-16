package ru.fmd.todo_service.todo_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TodoConfig {
    @Bean
    @LoadBalanced
    RestTemplate getrestTemplate(){ return new RestTemplate(); }
}
