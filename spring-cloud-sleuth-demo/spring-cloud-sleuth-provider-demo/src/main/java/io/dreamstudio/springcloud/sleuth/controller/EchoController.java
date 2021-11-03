package io.dreamstudio.springcloud.sleuth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky Fung
 */
@RestController
public class EchoController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    
    @GetMapping("/echo")
    public String echo(@RequestParam(value = "name", required = false) String name) {
        logger.info("echo name={}", name);
        return "echo "+name;
    }
}
