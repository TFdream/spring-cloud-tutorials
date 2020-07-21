package io.dreamstudio.springcloud.netflix.hystrix.concurrent;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ricky Fung
 */
public class TtlHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixThreadPoolProperties threadPoolProperties) {
        return this.doGetThreadPool(threadPoolKey, threadPoolProperties);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return this.doGetThreadPool(threadPoolKey, corePoolSize.get(), maximumPoolSize.get(), keepAliveTime.get(), unit, workQueue);
    }

    private ThreadPoolExecutor doGetThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixThreadPoolProperties properties) {
        final int dynamicCoreSize = properties.coreSize().get();
        final int keepAliveTime = properties.keepAliveTimeMinutes().get();
        final int maxQueueSize = properties.maxQueueSize().get();
        final BlockingQueue<Runnable> workQueue = getBlockingQueue(maxQueueSize);

        return doGetThreadPool(threadPoolKey, dynamicCoreSize, dynamicCoreSize, keepAliveTime, TimeUnit.MINUTES, workQueue);
    }

    private ThreadPoolExecutor doGetThreadPool(HystrixThreadPoolKey threadPoolKey,
                                               int corePoolSize, int maximumPoolSize,
                                               int keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        final ThreadFactory threadFactory = getThreadFactory(threadPoolKey);
        return new ThreadLocalThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory);
    }

    private static ThreadFactory getThreadFactory(final HystrixThreadPoolKey threadPoolKey) {
        return new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "hystrix-" + threadPoolKey.name() + "-" + threadNumber.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }

        };
    }
}
