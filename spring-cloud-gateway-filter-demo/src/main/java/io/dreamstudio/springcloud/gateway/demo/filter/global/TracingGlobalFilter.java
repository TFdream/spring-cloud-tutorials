package io.dreamstudio.springcloud.gateway.demo.filter.global;

import io.dreamstudio.springcloud.commons.Constants;
import io.dreamstudio.springcloud.commons.util.StringUtils;
import io.dreamstudio.springcloud.gateway.demo.filter.TracingContext;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Ricky Fung
 */
public class TracingGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = getTraceId(exchange);
        if (StringUtils.isEmpty(traceId)) { //产生traceId
            traceId = TracingContext.genTraceId();
        }
        //设置到MDC中
        TracingContext.put(traceId);

        try {
            final String traceLogId = traceId;
            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .headers(header -> header.set(Constants.TRACING_HEADER_NAME, traceLogId))
                    .build();

            //exchange变异，exchange中的原数据自动带入到新的Exchange中
            /*ServerWebExchange.Builder builder = exchange.mutate()
                    //增加response日志信息
                    .response(exchange.getResponse())
                    //对下游服务调用增加trace信息
                    .request(request);

            return chain.filter(builder.build());*/

            ServerWebExchange newExchange = exchange.mutate()
                    .request(request)
                    .build();
            return chain.filter(newExchange);
        } finally {
            TracingContext.remove();
        }
    }

    private String getTraceId(ServerWebExchange exchange) {
        //从上下文中获取
        Object traceId = exchange.getAttribute(Constants.TRACING_HEADER_NAME);
        if (traceId != null) {
            return String.valueOf(traceId);
        }
        //从请求参数中获取
        //String token = exchange.getRequest().getQueryParams().getFirst("Authorization");

        //从调用上游获取,支持：X-TraceId
        Object traceLogId = exchange.getRequest().getHeaders().getFirst(Constants.TRACING_HEADER_NAME);
        if (traceLogId != null) {
            Object prevLogId = exchange.getAttributes().putIfAbsent(Constants.TRACING_HEADER_NAME, traceLogId);
            if (prevLogId != null) {
                return String.valueOf(prevLogId);
            }
            return String.valueOf(traceLogId);
        }
        return null;
    }
}
