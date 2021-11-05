package io.dreamstudio.springcloud.eureka.discovery.provider.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Ricky Fung
 */
@RestController
public class EchoController {
    @Resource
    private ApplicationInfoManager aim;

    @GetMapping("/echo")
    public String echo(String name) {
        return "provider:" + name;
    }

    @GetMapping("/appInfo")
    public String appInfo(String name) {
        InstanceInfo instanceInfo = aim.getInfo();
        Map<String, String> map = instanceInfo.getMetadata();
        map.put("dynamic-s1", "value_2");
        return "provider:" + name;
    }

}
