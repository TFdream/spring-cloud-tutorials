package io.dreamstudio.springcloud.netflix.ribbon.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Ricky Fung
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Ribbon02ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ribbon02ConsumerApplication.class, args);
    }
}
