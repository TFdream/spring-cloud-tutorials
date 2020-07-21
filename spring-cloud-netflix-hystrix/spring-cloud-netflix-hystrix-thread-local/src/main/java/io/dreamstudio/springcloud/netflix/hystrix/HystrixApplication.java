package io.dreamstudio.springcloud.netflix.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import io.dreamstudio.springcloud.netflix.hystrix.concurrent.TtlHystrixConcurrencyStrategy;
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
public class HystrixApplication {

    public static void main(String[] args) {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(new TtlHystrixConcurrencyStrategy());
        SpringApplication.run(HystrixApplication.class, args);
    }
}
