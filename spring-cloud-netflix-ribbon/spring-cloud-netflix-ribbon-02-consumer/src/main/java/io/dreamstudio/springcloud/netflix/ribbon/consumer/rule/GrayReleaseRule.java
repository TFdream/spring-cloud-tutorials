package io.dreamstudio.springcloud.netflix.ribbon.consumer.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义灰度发布规则
 * 参考 com.alibaba.cloud.nacos.ribbon.NacosRule
 * @author Ricky Fung
 */
public class GrayReleaseRule extends AbstractLoadBalancerRule {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static final String GRAY_LABEL = "gray-label";

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public Server choose(Object key) {
        try {
            Map<String, String> metadata = this.nacosDiscoveryProperties.getMetadata();
            String grayLabel = metadata.get(GRAY_LABEL);

            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
            String name = loadBalancer.getName();

            NamingService namingService = nacosDiscoveryProperties
                    .namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(name, true);
            if (CollectionUtils.isEmpty(instances)) {
                LOG.warn("no instance in service {}", name);
                return null;
            }

            List<Instance> preferList = instances;
            if (StringUtils.isNotEmpty(grayLabel)) {
                //过滤相同的
                List<Instance> sameGrayInstances = instances.stream()
                        .filter(instance -> Objects.equals(grayLabel,
                                instance.getMetadata().get(GRAY_LABEL)))
                        .collect(Collectors.toList());

                if (CollectionUtils.isEmpty(sameGrayInstances)) {
                    LOG.error("满足条件的灰度服务提供者列表为空, name = {}, gray-label = {}, instances = {}",
                            name, grayLabel, instances);
                    return null;
                } else {
                    preferList = sameGrayInstances;
                }
            }

            Instance instance = ExtendBalancer.getHostByRandomWeight2(preferList);

            return new NacosServer(instance);
        } catch (Exception e) {
            LOG.error("Nacos Gray Rule error", e);
        }
        return null;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

}
