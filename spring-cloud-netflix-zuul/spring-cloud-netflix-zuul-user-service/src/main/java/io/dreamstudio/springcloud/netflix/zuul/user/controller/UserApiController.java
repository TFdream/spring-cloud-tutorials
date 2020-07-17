package io.dreamstudio.springcloud.netflix.zuul.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/api")
public class UserApiController {

    @Value("${server.port:}")
    private Integer serverPort;

    @Value("${spring.application.name:}")
    private String applicationName;

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable("message") String message) {
        return String.format("[%s : %s] hello %s", applicationName, serverPort, message);
    }
}
