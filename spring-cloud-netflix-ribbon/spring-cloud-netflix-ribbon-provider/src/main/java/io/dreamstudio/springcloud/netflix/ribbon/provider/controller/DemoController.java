package io.dreamstudio.springcloud.netflix.ribbon.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky Fung
 */
@RestController
public class DemoController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Value("${server.port:}")
    private Integer serverPort;

    @GetMapping(value = "/echo")
    public String echo(@RequestParam("name") String name) throws InterruptedException {

        // 模拟执行 100ms 时长。方便后续我们测试请求超时
        Thread.sleep(100L);

        // 记录被调用的日志
        LOG.info("[echo][被调用啦 name({})]", name);

        return serverPort + "-provider " + name;
    }
}
