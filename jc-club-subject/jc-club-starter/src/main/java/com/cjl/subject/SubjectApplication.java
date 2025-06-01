package com.cjl.subject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liang
 * @version 1.0
 * 2025-06-01-16:10
 * 刷题微服务启动类
 */
@SpringBootApplication
@ComponentScan("com.cjl")
@MapperScan("com.cjl.**.mapper")
public class SubjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class, args);
    }
}
