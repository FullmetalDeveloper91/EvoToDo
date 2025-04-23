package ru.fmd.task_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TaskConfig {
    @Bean
    @LoadBalanced
    RestTemplate getrestTemplate(){ return new RestTemplate(); }
}
