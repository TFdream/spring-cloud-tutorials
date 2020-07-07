package io.dreamstudio.springcloud.consul.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ricky Fung
 */
@Configuration
public class RestTemplateConfiguration {

    /**
     * 在RestTemplate实例化的地方添加了@LoadBalanced注解，这样在我们使用RestTemplate时就该注解就会自动将调用接口的地址替换成真正的服务地址
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 默认不带负载均衡的
     * @return
     */
    @Bean
    public RestTemplate defaultRestTemplate() {
        return new RestTemplate();
    }
}
