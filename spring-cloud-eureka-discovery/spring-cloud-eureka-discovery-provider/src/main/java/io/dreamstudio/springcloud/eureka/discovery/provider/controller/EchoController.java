package io.dreamstudio.springcloud.eureka.discovery.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky Fung
 */
@RestController
public class EchoController {

    @GetMapping("/echo")
    public String echo(String name) {
        return "provider:" + name;
    }
}
