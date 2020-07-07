package io.dreamstudio.springcloud.eureka.discovery.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Ricky Fung
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaProviderApplication.class, args);
    }

}
