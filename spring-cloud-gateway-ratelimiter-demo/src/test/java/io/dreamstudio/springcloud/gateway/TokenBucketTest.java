package io.dreamstudio.springcloud.gateway;

import io.dreamstudio.springcloud.gateway.demo.ratelimiter.TokenBucket;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class TokenBucketTest {

    @Test
    public void testApp() throws InterruptedException {
        //创建一个令牌桶（桶大小为 100，且每秒生成 100 个令牌）：
        //TokenBucket limiter = new TokenBucket(100, 100, 1000);
        TokenBucket limiter = new TokenBucket(100, 1, TimeUnit.SECONDS);
        int successCnt = 0;
        for (int i=0; i<500; i++) {
            boolean success = limiter.tryAcquire(1);
            if (success) {
                successCnt++;
                System.out.println("请求成功");
            } else {
                System.out.println("请求被限流");
            }
            if (i%50 == 0) {
                TimeUnit.MILLISECONDS.sleep(50);
            }
        }
        System.out.println("成功数量="+successCnt);
    }
}
