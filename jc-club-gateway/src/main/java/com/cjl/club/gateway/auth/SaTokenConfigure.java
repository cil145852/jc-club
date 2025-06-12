package com.cjl.club.gateway.auth;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-11-21:18
 * @Description [Sa-Token 权限认证] 配置类
 */

@Configuration
public class SaTokenConfigure {
    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截全部path
                .addInclude("/**")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    SaRouter.match("/**", "/auth/user/doLogin", r -> StpUtil.checkLogin());
                    SaRouter.match("/auth/**", "/auth/user/doLogin", r -> StpUtil.checkRole("admin"));
                    SaRouter.match("/oss/**", r -> StpUtil.checkLogin());
                    SaRouter.match("/subject/subject/add", r -> StpUtil.checkPermission("subject.add"));
                    SaRouter.match("/subject/**", r -> StpUtil.checkLogin());
                });
    }
}
