package io.dreamstudio.springcloud.gateway.demo.dynamic;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 发布
 * org.springframework.cloud.gateway.route.CachingRouteLocator 会订阅RefreshRoutesEvent事件
 * @author Ricky Fung
 */
public class GatewayRoutesRefresher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }

    public void refreshRoutes() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
