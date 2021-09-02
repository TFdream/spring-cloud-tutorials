package io.dreamstudio.springcloud.nacos.discovery.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky Fung
 */
@RestController
public class DemoController {

    @GetMapping(value = "/echo/{message}")
    public String echo(@PathVariable("message") String msg) {
        return "Hello Nacos Discovery " + msg;
    }
}
