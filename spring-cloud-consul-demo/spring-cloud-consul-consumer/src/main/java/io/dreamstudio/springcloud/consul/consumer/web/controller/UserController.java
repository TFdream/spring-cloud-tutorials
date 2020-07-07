package io.dreamstudio.springcloud.consul.consumer.web.controller;

import io.dreamstudio.springcloud.consul.consumer.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/api/client")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/call1")
    public String testCallA() {
        return userService.getUserList1();
    }

    @GetMapping("/call2")
    public String testCallB() {
        return userService.getUserList2();
    }
}
