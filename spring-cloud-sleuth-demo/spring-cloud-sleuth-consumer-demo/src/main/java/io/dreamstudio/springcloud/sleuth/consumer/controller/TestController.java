package io.dreamstudio.springcloud.sleuth.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/api")
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    
    @Resource
    private RestTemplate restTemplate;
    
    @GetMapping("/trace")
    public String trace(@RequestParam(value = "name", required = false) String name) {
        logger.info(" === call trace === "+name);
        return restTemplate.getForEntity("http://spring-cloud-sleuth-provider-demo/echo?name="+name, String.class).getBody();
    }
}
