package io.dreamstudio.springcloud.resilience4j.web.controller;

import io.dreamstudio.springcloud.commons.ResponseDTO;
import io.dreamstudio.springcloud.resilience4j.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{groupId}/list")
    public ResponseDTO getUsers(@PathVariable("groupId") Integer groupId) throws Exception {
        return userService.getUsers(groupId);
    }
}
