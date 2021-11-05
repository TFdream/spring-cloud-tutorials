package io.dreamstudio.springcloud.gateway.dynamic.config;

import io.dreamstudio.springcloud.gateway.dynamic.route.DynamicRouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 底层原理参考 GatewayAutoConfiguration
 * RouteDefinitionRepository
 * RouteRefreshListener
 * @author Ricky Fung
 */
@Configuration
public class DynamicGatewayConfig {

    @Bean
    public RouteDefinitionRepository dynamicRouteDefinitionLocator() {
        return new DynamicRouteDefinitionLocator();
    }

}
