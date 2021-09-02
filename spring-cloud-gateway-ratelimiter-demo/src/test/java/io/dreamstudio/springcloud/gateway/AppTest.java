package io.dreamstudio.springcloud.gateway;

import org.junit.Test;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;

/**
 * @author Ricky Fung
 */
public class AppTest {

    @Test
    public void testApp() {
        RequestRateLimiterGatewayFilterFactory configuration = new RequestRateLimiterGatewayFilterFactory(null, null);
    }
}
