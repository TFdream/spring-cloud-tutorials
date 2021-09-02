package io.dreamstudio.springcloud.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考资料：https://resilience4j.readme.io/docs/getting-started-3
 * @author Ricky Fung
 */
@SpringBootApplication
public class Resilience4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jApplication.class, args);
    }
}
