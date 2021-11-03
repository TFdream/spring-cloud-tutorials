package io.dreamstudio.springcloud.sleuth.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Ricky Fung
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ClientApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class, args);
    }
}
