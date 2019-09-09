package io.dreamstudio.springcloud.eureka.web.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ricky Fung
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    private final String serviceId = "spring-cloud-consul-provider";

    @Resource
    private LoadBalancerClient loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 获取所有服务
     */
    @RequestMapping("/services")
    public List<ServiceInstance> services() {
        return discoveryClient.getInstances(serviceId);
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancer.choose(serviceId).getUri().toString();
    }

    /**
     * 使用均衡负载的形式去获取服务端提供的服务
     * @return
     */
    @RequestMapping("/call")
    public String call() {
        ServiceInstance serviceInstance = loadBalancer.choose("service-producer");
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/echo/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }

}
