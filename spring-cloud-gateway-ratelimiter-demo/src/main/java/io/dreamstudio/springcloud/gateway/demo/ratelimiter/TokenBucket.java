package io.dreamstudio.springcloud.gateway.demo.ratelimiter;

import java.util.concurrent.TimeUnit;

/**
 * 令牌桶算法
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
