package io.dreamstudio.springcloud.gateway;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class GuavaRateLimiterTest {

    @Test
    public void testRateLimiter() {
        RateLimiter rateLimiter = RateLimiter.create(20, 1, TimeUnit.MINUTES);
        for (int i=0; i<200; i++) {
            System.out.println(rateLimiter.tryAcquire(1));
        }
    }
}
