package ru.fmd.log_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TaskLogConfig {
    @Bean
    @LoadBalanced
    RestTemplate getrestTemplate(){ return new RestTemplate(); }
}
