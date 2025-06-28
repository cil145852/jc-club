package com.cjl.subject.application.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-28-17:03
 * @Description
 */
@Configuration
public class FeignConfiguration {

   @Bean
   public FeignRequestInterceptor feignRequestInterceptor() {
      return new FeignRequestInterceptor();
   }
}
