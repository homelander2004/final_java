package com.igorblazhko.booking.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class IgorBlazhkoAsyncConfig {

    @Bean(name = "igorBlazhkoTaskExecutor")
    public Executor igorBlazhkoTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("igor-blazhko-async-");
        executor.initialize();
        return executor;
    }
}