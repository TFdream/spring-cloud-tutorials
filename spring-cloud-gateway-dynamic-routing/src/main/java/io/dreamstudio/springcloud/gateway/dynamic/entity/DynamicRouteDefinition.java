package io.dreamstudio.springcloud.gateway.dynamic.entity;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class DynamicRouteDefinition {
    /**
     * 路由id
     */
    private String routeId;
    /**
     * 跳转地址uri
     */
    private String routeUri;
    /**
     * 路由顺序
     */
    private Integer routeOrder;

    private Integer status;

    /**
     * 路由说明
     */
    private String remark;

    /**
     * 路由谓词
     */
    private List<PredicateDefinition> predicates;
    /**
     * 过滤器
     */
    private List<FilterDefinition> filters;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteUri() {
        return routeUri;
    }

    public void setRouteUri(String routeUri) {
        this.routeUri = routeUri;
    }

    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PredicateDefinition> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<PredicateDefinition> predicates) {
        this.predicates = predicates;
    }

    public List<FilterDefinition> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDefinition> filters) {
        this.filters = filters;
    }
}
