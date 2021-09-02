package io.dreamstudio.springcloud.gateway.demo.ratelimiter;

import java.util.concurrent.TimeUnit;

/**
 * 令牌桶算法
 * 一般会有两种不同的实现方式：
 * 1. 第一种方式是启动一个内部线程，不断的往桶中添加令牌，处理请求时从桶中获取令牌，和上面图中的处理逻辑一样。
 * 2. 第二种方式不依赖于内部线程，而是在每次处理请求之前先实时计算出要填充的令牌数并填充，然后再从桶中获取令牌。
 * @author Ricky Fung
 */
public class TokenBucket {
    private final long capacity;
    private final double refillTokensPerMillis;
    private double availableTokens;
    private long lastRefillTimestamp;

    public TokenBucket(long capacity, long time, TimeUnit unit) {
        this(capacity, capacity, unit.toMillis(time));
    }

    public TokenBucket(long capacity, long refillTokens, long refillPeriodMillis) {
        this.capacity = capacity;
        this.refillTokensPerMillis = (double) refillTokens / (double) refillPeriodMillis;
        this.availableTokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire(int numberTokens) {
        refill();
        if (availableTokens < numberTokens) {
            return false;
        } else {
            availableTokens -= numberTokens;
            return true;
        }
    }

    private void refill() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > lastRefillTimestamp) {
            long millisSinceLastRefill = currentTimeMillis - lastRefillTimestamp;
            double refill = millisSinceLastRefill * refillTokensPerMillis;
            this.availableTokens = Math.min(capacity, availableTokens + refill);
            this.lastRefillTimestamp = currentTimeMillis;
        }
    }
}
