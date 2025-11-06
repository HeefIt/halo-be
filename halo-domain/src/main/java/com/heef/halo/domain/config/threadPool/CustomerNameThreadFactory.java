package com.heef.halo.domain.config.threadPool;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂 (主要修改了线程的打印名字)
 *
 * @author heefM
 * @date 2025-10-31
 */
public class CustomerNameThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger poolNumber = new AtomicInteger(1);
    private final String namePrefix;

    public CustomerNameThreadFactory(String name) {
        group = Thread.currentThread().getThreadGroup(); // 直接获取当前线程组
        if (StringUtils.isBlank(name)) {
            name = "pool";
        }
        namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + Thread.currentThread().getId());
        t.setDaemon(false); // 默认非守护线程
        t.setPriority(Thread.NORM_PRIORITY); // 默认优先级
        return t;
    }


}