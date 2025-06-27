package com.cjl.subject.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-27-20:16
 * @Description 线程池配置类
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor labelThreadPool() {
        return new ThreadPoolExecutor(
                15,
                100,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(40),
                new CustomNameThreadFactory("label"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
