package com.agoda.assessment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final int queueCapacity;
    private final String threadNamePrefix;

    @Autowired
    public AsyncConfig(@Value("${app.async.pool.coreSize}") int corePoolSize,
                       @Value("${app.async.pool.maxSize}") int maxPoolSize,
                       @Value("${app.async.queueCapacity}") int queueCapacity,
                       @Value("${app.async.thread.namePrefix}") String threadNamePrefix) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.queueCapacity = queueCapacity;
        this.threadNamePrefix = threadNamePrefix;
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();

        return executor;
    }

}
