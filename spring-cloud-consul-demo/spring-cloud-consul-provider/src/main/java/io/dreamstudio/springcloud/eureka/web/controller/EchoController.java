package io.dreamstudio.springcloud.eureka.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/echo")
public class EchoController {

    @GetMapping("/hello")
    public String list() {
        return "Hello, SC";
    }
}
