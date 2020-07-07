package io.dreamstudio.springcloud.eureka.discovery.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@RestController
public class TestController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final String SERVICE_ID = "spring-cloud-eureka-discovery-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/echo")
    public String echo(String name) {
        String reqUrl = String.format("http://%s/echo?name=%s", SERVICE_ID, name);
        String response = restTemplate.getForObject(reqUrl, String.class);
        LOG.info("请求服务接口:{}", reqUrl);
        return response;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
