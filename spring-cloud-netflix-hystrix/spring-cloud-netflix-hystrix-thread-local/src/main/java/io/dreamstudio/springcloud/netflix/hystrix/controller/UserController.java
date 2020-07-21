package io.dreamstudio.springcloud.netflix.hystrix.controller;

import io.dreamstudio.springcloud.netflix.hystrix.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") Long uid) {
        return userService.getUserById(uid);
    }
}
