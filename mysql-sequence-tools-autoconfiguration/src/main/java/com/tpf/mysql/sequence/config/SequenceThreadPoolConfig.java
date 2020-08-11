package com.tpf.mysql.sequence.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p> </p >
 *
 * @author : tianpf
 * @version :  SequenceThreadPoolConfig.java,v 1.0, 2020/8/11-10:23 Exp $
 */
@Configuration
public class SequenceThreadPoolConfig {

    @Value("${threadPoolSize.asyncTask.corePoolSize:2}")
    private Integer corePoolSize;
    @Value("${threadPoolSize.asyncTask.maxPoolSize:10}")
    private Integer maxPoolSize;
    @Value("${threadPoolSize.asyncTask.queueCapacity:1000}")
    private Integer queueCapacity;
    @Value("${threadPoolSize.asyncTask.keepAliveSeconds:120}")
    private Integer keepAliveSeconds;

    @Bean
    public ThreadPoolTaskExecutor sequenceThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("AsyncTask");
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        //线程活跃时间
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //CallerRunsPolicy拒绝策略:用于被拒绝任务的处理程序，它直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskExecutor;
    }


}
