package io.dreamstudio.springcloud.netflix.hystrix.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 修饰线程池
 * 省去每次Runnable和Callable传入线程池时的修饰，这个逻辑可以在线程池中完成。
 * @author Ricky Fung
 */
public class TtlWrapExecutorDemo {
    static TransmittableThreadLocal<String> context = new TransmittableThreadLocal<String>();
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    //修饰线程池
    static ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);

    public static void main(String[] args) {
        new TtlWrapExecutorDemo().testTtlExecutor();
    }

    private void testTtlExecutor() {

        context.set("value-set-in-parent");

        Runnable task = new RunnableTask();
        Callable call = new CallableTask();

        ttlExecutorService.submit(task);
        ttlExecutorService.submit(call);

        ttlExecutorService.shutdown();
    }

    static class CallableTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("thread:"+Thread.currentThread().getName()+" get context:"+context.get());
            return null;
        }
    }

    static class RunnableTask implements Runnable {

        @Override
        public void run() {
            System.out.println("thread:"+Thread.currentThread().getName()+" get context:"+context.get());
        }
    }
}
