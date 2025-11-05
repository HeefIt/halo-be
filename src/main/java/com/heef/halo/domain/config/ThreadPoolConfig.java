package com.heef.halo.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "LabelThreadPool")
    public ThreadPoolExecutor labelThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                20,                              //核心线程数
                100,                                         //最大线程数
                5,                                           //非核心线程存活时间
                TimeUnit.SECONDS,                            //时间单位
                new LinkedBlockingQueue<>(60),            //任务队列
                new CustomerNameThreadFactory("label--") ,    //线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy());      //拒绝策略 CallerRunsPolicy线程池满时，尝试在调用者线程中执行任务
        return threadPoolExecutor;
    }

    @Bean(name = "infoThreadPool")
    public ThreadPoolExecutor infoThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                20,                              //核心线程数
                100,                                         //最大线程数
                5,                                           //非核心线程存活时间
                TimeUnit.SECONDS,                            //时间单位
                new LinkedBlockingQueue<>(60),            //任务队列 (分有界,无界,优先级无界)
                new CustomerNameThreadFactory("info--") ,    //线程工厂(自定义线程工厂)
                new ThreadPoolExecutor.CallerRunsPolicy());      //拒绝策略 CallerRunsPolicy线程池满时，尝试在调用者线程中执行任务
        return threadPoolExecutor;
    }


}
