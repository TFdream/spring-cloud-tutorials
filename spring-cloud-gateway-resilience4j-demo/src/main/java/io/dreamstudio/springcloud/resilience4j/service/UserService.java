package io.dreamstudio.springcloud.resilience4j.service;

import io.dreamstudio.springcloud.commons.ResponseDTO;
import io.dreamstudio.springcloud.commons.entity.User;
import io.dreamstudio.springcloud.commons.util.RandomUtils;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Ricky Fung
 */
@Service
public class UserService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallBack")
    public ResponseDTO getUsers(Integer groupId) throws TimeoutException, InterruptedException {
        List<User> users = remoteProcess(groupId);
        io.github.resilience4j.circuitbreaker.CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("backendA");
        LOG.info("CircuitBreaker={}", cb);
        return ResponseDTO.ok(users);
    }

    private List<User> fallBack(Throwable throwable){
        LOG.info(throwable.getLocalizedMessage() + ",方法被降级了~~");
        List<User> users = new ArrayList();
        return users;
    }

    private List<User> fallBack(CallNotPermittedException e){
        LOG.info("熔断器已经打开，拒绝访问被保护方法~");
        List<User> users = new ArrayList();
        return users;
    }

    private List<User> remoteProcess(Integer groupId) throws InterruptedException, TimeoutException {
        int millis = groupId + RandomUtils.nextInt(200, 3000);
        if (millis > 1000) {
            throw new TimeoutException("请求超时"+millis);
        }
        TimeUnit.MILLISECONDS.sleep(millis);
        List<User> users = new ArrayList();
        users.add(new User());
        return users;
    }

}
