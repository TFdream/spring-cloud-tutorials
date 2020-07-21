package io.dreamstudio.springcloud.netflix.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private RestTemplate restTemplate;

    @HystrixCommand(commandKey = "getUserById", groupKey = "user-service",
            fallbackMethod = "getDefaultUser")
    public String getUserById(Long id) {
        LOG.info("请求开始id:{}", id);
        String url = "http://user-service/api/users/" + id;
        String json = restTemplate.getForObject(url, String.class);
        LOG.info("请求接口:{} 返回结果:{}", url, json);
        return json;
    }

    /**
     * 指定的备用方法和原方法的参数个数或类型不同造成的，所以需要统一参数的类型和个数。
     * @param id
     * @return
     */
    public String getDefaultUser(Long id) {
        return "fallback "+id;
    }
}
