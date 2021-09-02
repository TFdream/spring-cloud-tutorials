package io.dreamstudio.springcloud.nacos.discovery.consumer.controller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@RestController
public class TestController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "/echo/{message}", method = RequestMethod.GET)
    public String echo(@PathVariable("message") String str) {
        String response = restTemplate.getForObject("http://spring-cloud-nacos-discovery-provider/echo/" + str, String.class);
        return response;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
