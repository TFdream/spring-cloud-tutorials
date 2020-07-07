package io.dreamstudio.springcloud.consul.consumer.service;

import io.dreamstudio.springcloud.consul.consumer.constant.Constant;
import io.dreamstudio.springcloud.consul.consumer.feign.ServerFeignApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@Service
public class UserService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LoadBalancerClient loadBalancerClient;

    //带负载均衡
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RestTemplate defaultRestTemplate;

    @Resource
    private ServerFeignApi serverFeignApi;

    /**
     * 方式三：使用OpenFeign
     * @return
     */
    public String getUserList3() {
        String result = serverFeignApi.getUsers();
        LOG.info("方式三 返回结果:{}", result);
        return result;
    }

    /**
     * 方式二：SpringCloud中为我们提供了@LoadBalanced注解，只要将该注解添加到RestTemplate中的获取的地方就可以了
     * @return
     */
    public String getUserList2() {
        String reqUrl = String.format("http://%s/api/server/users", Constant.PROVIDER_SERVICE_ID);
        String result = restTemplate.getForObject(reqUrl, String.class);
        LOG.info("方式二-请求服务接口地址:{} 返回结果:{}", reqUrl, result);
        return result;
    }

    /**
     * 方式一：LoadBalancerClient + RestTemplate
     * @return
     */
    public String getUserList1() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(Constant.PROVIDER_SERVICE_ID);
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());

        String reqUrl = String.format("http://%s:%s/api/server/users", serviceInstance.getHost(), serviceInstance.getPort());
        LOG.info("请求服务接口地址:{}", reqUrl);

        String response = defaultRestTemplate.getForObject(reqUrl, String.class);
        LOG.info("请求服务接口地址:{} 返回结果:{}", reqUrl, response);
        return response;
    }
}
