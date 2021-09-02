package io.dreamstudio.springcloud.gateway.demo.config;

import io.dreamstudio.springcloud.gateway.demo.filter.LocalRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ricky Fung
 */
@Configuration
public class GatewayConfig {

    @Bean
    public LocalRateLimiterGatewayFilterFactory LocalRateLimiterGatewayFilterFactory() {
        return new LocalRateLimiterGatewayFilterFactory();
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8080"))
                .build();
    }
}
