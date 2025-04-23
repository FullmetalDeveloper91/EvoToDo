package ru.fmd.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route( p -> p
                        .path("/user/**")
                        .filters(f->f.prefixPath("/api/v1"))
                        .uri("lb://USER-SERVICE"))
                .route(p -> p
                        .path("/task/**")
                        .filters(f -> f.prefixPath("/api/v1"))
                        .uri("lb://TASK-SERVICE")
                )
                .route(p -> p
                        .path("/log/**")
                        .filters(f->f.prefixPath("/api/v1"))
                        .uri("lb://LOG-SERVICE")
                )
                .build();
    }
}