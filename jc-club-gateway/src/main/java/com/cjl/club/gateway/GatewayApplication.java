package com.cjl.club.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-11-8:25
 * @Description
 */
@SpringBootApplication
@ComponentScan("com.cjl")
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
