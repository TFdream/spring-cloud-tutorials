package io.dreamstudio.springcloud.consul.consumer.feign;

import io.dreamstudio.springcloud.consul.consumer.constant.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ricky Fung
 */
@FeignClient(name = Constant.PROVIDER_SERVICE_ID)
public interface ServerFeignApi {

    @GetMapping("/api/server/users")
    String getUsers();
}
