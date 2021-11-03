package io.dreamstudio.springcloud.sleuth.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced // ribbon是客户端 的负载均衡工具
    //默认算法是轮询算法 核心组件IRule
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}