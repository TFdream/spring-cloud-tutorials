package io.dreamstudio.springcloud.netflix.ribbon.consumer.config;

import com.netflix.loadbalancer.IRule;
import io.dreamstudio.springcloud.netflix.ribbon.consumer.rule.GrayReleaseRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon 自定义规则
 */
@Configuration
public class MyRuleConfig {

    @Bean
    public IRule getRule(){
        return new GrayReleaseRule();
    }

}