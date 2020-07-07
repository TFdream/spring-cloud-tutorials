package io.dreamstudio.springcloud.consul.consumer.web.controller;

import io.dreamstudio.springcloud.consul.consumer.constant.Constant;
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
public class CallController {

    @Resource
    private LoadBalancerClient loadBalancerClient;
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 获取所有服务
     */
    @RequestMapping("/services")
    public List<ServiceInstance> services() {
        return discoveryClient.getInstances(Constant.PROVIDER_SERVICE_ID);
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancerClient.choose(Constant.PROVIDER_SERVICE_ID).getUri().toString();
    }

    /**
     * 使用均衡负载的形式去获取服务端提供的服务
     * @return
     */
    @RequestMapping("/call")
    public String call() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(Constant.PROVIDER_SERVICE_ID);
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/echo/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }

}
