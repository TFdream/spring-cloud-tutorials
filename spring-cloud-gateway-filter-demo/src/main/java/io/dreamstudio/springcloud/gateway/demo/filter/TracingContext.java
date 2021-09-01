package io.dreamstudio.springcloud.gateway.demo.filter;

import io.dreamstudio.springcloud.commons.Constants;
import org.slf4j.MDC;
import java.util.UUID;

/**
 * @author Ricky Fung
 */
public class TracingContext {

    public static void put(String traceId) {
        MDC.put(Constants.MDC_KEY_NAME, traceId);
    }

    public static void remove() {
        MDC.remove(Constants.MDC_KEY_NAME);
    }

    public static String get() {
        return MDC.get(Constants.MDC_KEY_NAME);
    }

    public static String genTraceId() {
        return UUID.randomUUID().toString();
    }
}
