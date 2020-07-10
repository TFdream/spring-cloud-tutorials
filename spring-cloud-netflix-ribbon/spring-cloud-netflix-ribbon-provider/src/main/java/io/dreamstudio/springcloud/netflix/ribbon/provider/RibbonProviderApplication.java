package io.dreamstudio.springcloud.netflix.ribbon.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Ricky Fung
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RibbonProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonProviderApplication.class, args);
    }
}
