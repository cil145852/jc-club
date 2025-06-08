package com.cjl.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-08-21:19
 * @Description oss服务启动器
 */
@SpringBootApplication
@ComponentScan("com.cjl")
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
