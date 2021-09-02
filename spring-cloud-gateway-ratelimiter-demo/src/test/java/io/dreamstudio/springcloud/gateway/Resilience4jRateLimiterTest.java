package io.dreamstudio.springcloud.gateway;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.internal.AtomicRateLimiter;
import io.github.resilience4j.ratelimiter.internal.SemaphoreBasedRateLimiter;
import org.junit.Test;

import java.time.Duration;

/**
 * SemaphoreBasedRateLimiter 基于信号量实现，用户的每次请求都会申请一个信号量，并记录申请的时间，申请通过则允许请求，申请失败则限流，另外有一个内部线程会定期扫描过期的信号量并释放，很显然这是令牌桶的算法。
 * AtomicRateLimiter 和上面的经典实现类似，不需要额外的线程，在处理每次请求时，根据距离上次请求的时间和生成令牌的速度自动填充。
 * @author Ricky Fung
 */
public class Resilience4jRateLimiterTest {

    @Test
    public void testSemaphore() {
        // 创建一个 RateLimiter，每秒允许一次请求
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(100))
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(1)
                .build();

        RateLimiter rateLimiter = new SemaphoreBasedRateLimiter("semaphore", rateLimiterConfig);

        for (int i=0; i<200; i++) {
            System.out.println(rateLimiter.acquirePermission(1));
        }
    }

    @Test
    public void testAtomic() {
        // 创建一个 RateLimiter，每秒允许一次请求
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(100))
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(1)
                .build();
        RateLimiter rateLimiter = new AtomicRateLimiter("backendName", rateLimiterConfig);

        for (int i=0; i<200; i++) {
            System.out.println(rateLimiter.acquirePermission(1));
        }
    }
}
