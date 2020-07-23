package io.dreamstudio.springcloud.netflix.hystrix.config;

import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟从Apollo中读取配置
 * @author Ricky Fung
 */
@Component
public class ApolloPolledConfigSource implements PolledConfigurationSource {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {

        Map<String, Object> complete = new HashMap<>();

        /**
         * 配置中心配置了对应的key/value。其中commandKey是我指定的具体接口。
         */
        complete.put("hystrix.command.getUserById.execution.isolation.thread.timeoutInMilliseconds", "2000");
        complete.put("hystrix.command.getUserById.execution.isolation.strategy", "THREAD");

        LOG.info("轮询配置 initial:{}, checkPoint:{} 结果:{}", initial, checkPoint, complete);

        return PollResult.createFull(complete);
    }
}
