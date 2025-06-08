package com.cjl.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-08-21:35
 * @Description
 */
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {
    /**
     * minio地址
     */
    private String url;
    /**
     * minio账户
     */
    private String accessKey;
    /**
     * minio密码
     */
    private String secretKey;
}
