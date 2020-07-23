package io.dreamstudio.springcloud.netflix.hystrix.config;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ricky Fung
 */
@Configuration
public class ApolloDynamicConfig {

    @Bean
    public DynamicConfiguration dynamicConfiguration(ApolloPolledConfigSource apolloPolledConfigSource) {
        DynamicConfiguration configuration = new DynamicConfiguration(apolloPolledConfigSource,
                new FixedDelayPollingScheduler(60 * 1000, 5 * 1000, false));

        ConfigurationManager.install(configuration);// 安裝后会启动schedel,定时调用DynamicConfigSource.poll()更新配置
        return configuration;
    }
}
