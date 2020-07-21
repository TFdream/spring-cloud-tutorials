package io.dreamstudio.springcloud.netflix.hystrix.ttl;

import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用TtlRunnable和TtlCallable来修饰传入线程池的Runnable和Callable。
 *
 * @author Ricky Fung
 */
public class TtlWrapRunnableDemo {
    static ThreadLocal<String> context = new InheritableThreadLocal<>();
    static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        new TtlWrapRunnableDemo().testRunnable();
    }

    private void testRunnable() {

        context.set("value-set-in-parent");

        Runnable task = new Runnable(){
            @Override
            public void run() {
                System.out.println("get:"+context.get());
            }
        };
        // 额外的处理，生成修饰了的对象ttlRunnable
        Runnable ttlRunnable = TtlRunnable.get(task);
        pool.submit(ttlRunnable);

        pool.shutdown();
    }
}
