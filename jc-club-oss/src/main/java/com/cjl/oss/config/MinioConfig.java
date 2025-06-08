package com.cjl.oss.config;

import com.cjl.oss.properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-08-21:36
 * @Description
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {
    @Resource
    private MinioProperties minioProperties;

    /**
     * 创建MinioClient对象并放入容器中
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
