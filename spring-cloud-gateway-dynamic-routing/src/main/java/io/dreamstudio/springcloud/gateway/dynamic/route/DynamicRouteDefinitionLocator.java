package io.dreamstudio.springcloud.gateway.dynamic.route;

import io.dreamstudio.springcloud.commons.util.JsonUtils;
import io.dreamstudio.springcloud.gateway.dynamic.entity.DynamicRouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 参考 actuate模块中的 AbstractGatewayControllerEndpoint
 * 对比 InMemoryRouteDefinitionRepository
 * @author Ricky Fung
 */
public class DynamicRouteDefinitionLocator implements RouteDefinitionRepository, ApplicationEventPublisherAware {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final Map<String, RouteDefinition> routesMap = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<RouteDefinition> routesList = new CopyOnWriteArrayList<>();
    
    private ApplicationEventPublisher publisher;
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routesMap.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (StringUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            routesMap.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routesMap.containsKey(id)) {
                routesMap.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(
                    new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }

    //========
    public List<RouteDefinition> initRoutList(boolean init) {
        LOG.info("gateway动态路由加载开始, init={}", init);
        try {
            List<DynamicRouteDefinition> list = loadRoutingDefinitions();
            if (CollectionUtils.isEmpty(list)) {
                LOG.info("gateway动态路由加载, 路由列表为空");
                routesMap.clear();
                return null;
            }

            Map<String, RouteDefinition> tmpRoutes = new HashMap<>();
            for (DynamicRouteDefinition d : list) {
                String id = d.getRouteId();
                if (StringUtils.isEmpty(d.getRouteId())) {
                    LOG.warn("gateway动态路由加载, 路由ID为空={}", JsonUtils.toJson(d));
                    continue;
                }
                if(CollectionUtils.isEmpty(d.getPredicates())) {
                    LOG.warn("gateway动态路由加载, ID={} 谓词列表为空={}", id, JsonUtils.toJson(d));
                }
                if(d.getFilters() == null || d.getFilters().isEmpty()){
                    LOG.warn("gateway动态路由加载, ID={} 过滤器列表为空={}", id, JsonUtils.toJson(d));
                }
                RouteDefinition routeDefinition = new RouteDefinition();
                routeDefinition.setId(id);
                routeDefinition.setFilters(d.getFilters());
                routeDefinition.setPredicates(d.getPredicates());
                routeDefinition.setUri(URI.create(d.getRouteUri()));
                routeDefinition.setOrder(d.getRouteOrder());

                tmpRoutes.put(id, routeDefinition);
            }
            routesMap.clear();
            routesMap.putAll(tmpRoutes);

            LOG.info("gateway动态路由加载结束, 路由列表为空");

        } catch (Exception ex) {
            LOG.error("发生路由变更事件,本地路由刷新执行异常 routeRefreshFlag={} curRouteRefreshFlag={} ", ex);
        } finally {
            refreshRoutes();
        }
        return null;
    }
    
    /**
     * 发送路由刷新通知事件
     * gateway模块中CachingRouteLocator 负责处理该事件
     */
    protected void refreshRoutes() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
    
    protected List<DynamicRouteDefinition> loadRoutingDefinitions() {
        return null;
    }
    
}
