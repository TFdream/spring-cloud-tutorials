package io.dreamstudio.springcloud.netflix.zuul.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/api")
public class UserApiController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Value("${server.port:}")
    private Integer serverPort;

    @Value("${spring.application.name:}")
    private String applicationName;

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable("message") String message) {
        return String.format("[%s : %s] hello %s", applicationName, serverPort, message);
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") Long id) throws InterruptedException {
        int sleep = new Random().nextInt(8);
        if (sleep < 1) {
            sleep = 1;
        }
        LOG.info("本次调用sleep:{}", sleep);
        TimeUnit.SECONDS.sleep(sleep);
        return String.format("[%s : %s] getUserById %s", applicationName, serverPort, id);
    }
}
