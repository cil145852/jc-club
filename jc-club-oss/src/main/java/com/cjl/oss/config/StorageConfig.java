package com.cjl.oss.config;

import com.cjl.oss.adapter.MinioStorageAdapter;
import com.cjl.oss.adapter.StorageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-09-15:26
 * @Description
 */
@Configuration
@RefreshScope
public class StorageConfig {
    @Value("${storage.service.type}")
    private String storageServiceType;

    @Bean
    @RefreshScope
    public StorageAdapter storageService() {
        if ("minio".equals(storageServiceType)) {
            return new MinioStorageAdapter();
        } else {
            throw new RuntimeException("不支持该存储服务");
        }
    }

}
