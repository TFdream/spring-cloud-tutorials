package io.dreamstudio.springcloud.netflix.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author Ricky Fung
 */
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication
public class HystrixDynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDynamicApplication.class, args);
    }
}
