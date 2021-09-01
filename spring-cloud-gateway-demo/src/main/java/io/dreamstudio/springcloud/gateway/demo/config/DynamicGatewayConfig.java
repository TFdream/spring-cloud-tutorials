package io.dreamstudio.springcloud.gateway.demo.config;

import io.dreamstudio.springcloud.gateway.demo.dynamic.DynamicRouteDefinitionLocator;
import io.dreamstudio.springcloud.gateway.demo.dynamic.GatewayRoutesRefresher;
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

    @Bean
    public GatewayRoutesRefresher routesRefresher() {
        return new GatewayRoutesRefresher();
    }

}
