package io.dreamstudio.springcloud.consul.provider.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ricky Fung
 */
public abstract class SeqUtils {
    private static final AtomicLong COUNTER = new AtomicLong(1);

    public static long nextId() {
        return COUNTER.getAndIncrement();
    }
}
