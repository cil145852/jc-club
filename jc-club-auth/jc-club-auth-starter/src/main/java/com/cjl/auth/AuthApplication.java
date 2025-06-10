package com.cjl.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-10-21:01
 * @Description
 */
@SpringBootApplication
@ComponentScan("com.cjl")
@MapperScan("com.cjl.**.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
