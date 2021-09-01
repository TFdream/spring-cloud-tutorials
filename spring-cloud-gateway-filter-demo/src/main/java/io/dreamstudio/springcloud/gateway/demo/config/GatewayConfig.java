package io.dreamstudio.springcloud.gateway.demo.config;

import io.dreamstudio.springcloud.gateway.demo.filter.RequestTimeGatewayFilterFactory;
import io.dreamstudio.springcloud.gateway.demo.filter.global.TracingGlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ricky Fung
 */
@Configuration
public class GatewayConfig {

    /**
     * GlobalFilter示例
     * @return
     */
    @Bean
    public TracingGlobalFilter tracingGlobalFilter() {
        return new TracingGlobalFilter();
    }

    /**
     * GatewayFilter示例
     * @return
     */
    @Bean
    public RequestTimeGatewayFilterFactory requestTimeGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/user/getByUsername")
                        .uri("http://localhost:8080/user/getByUsername"))
                .build();
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
