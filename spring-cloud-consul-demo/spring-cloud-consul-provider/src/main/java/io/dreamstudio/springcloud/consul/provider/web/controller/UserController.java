package io.dreamstudio.springcloud.consul.provider.web.controller;

import io.dreamstudio.springcloud.consul.provider.entity.UserDTO;
import io.dreamstudio.springcloud.consul.provider.util.SeqUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端接口
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/api/server")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/users")
    public List<UserDTO> list() {
        LOG.info("查询用户列表开始");
        List<UserDTO> userList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(SeqUtils.nextId());
            userDTO.setName("张三丰"+i);
            userList.add(userDTO);
        }
        return userList;
    }
}
