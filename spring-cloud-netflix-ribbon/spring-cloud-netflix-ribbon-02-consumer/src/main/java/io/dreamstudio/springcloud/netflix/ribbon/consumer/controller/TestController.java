package io.dreamstudio.springcloud.netflix.ribbon.consumer.controller;

import io.dreamstudio.springcloud.netflix.ribbon.consumer.rule.GrayReleaseRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Ricky Fung
 */
@RestController
public class TestController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String serviceId = "netflix-ribbon-provider";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/hello")
    public String hello(String name) {
        // 获得服务 `demo-provider` 的一个实例
        ServiceInstance instance = loadBalancerClient.choose(serviceId);
        Map<String, String> map = instance.getMetadata();
        String grayLabel = map.get(GrayReleaseRule.GRAY_LABEL);

        // 发起调用
        String targetUrl = instance.getUri() + "/echo?name=" + name;
        try {
            String response = new RestTemplate().getForObject(targetUrl, String.class);
            // 返回结果
            return "consumer:" + response + " grayLabel:"+grayLabel;
        } catch (Exception e) {
            LOG.error(String.format("请求服务 %s 实例:%s 异常", serviceId, targetUrl), e);
        }
        return "error";
    }

    /**
     * 直接使用
     * @param name
     * @return
     */
    @GetMapping("/hello02")
    public String hello02(String name) {
        // 直接使用 RestTemplate 调用服务 `demo-provider`
        String targetUrl = String.format("http://%s/echo?name=", serviceId, name);
        String response = restTemplate.getForObject(targetUrl, String.class);
        // 返回结果
        return "consumer:" + response;
    }
}
