package com.cjl.club.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-27-21:54
 * @Description 登录拦截器
 */

@Component
public class LoginFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if ("/auth/user/doLogin".equals(path)) {
            return chain.filter(exchange);
        }
        String loginId = (String) StpUtil.getTokenInfo().getLoginId();
        if (!StringUtils.hasText(loginId)) {
            throw new RuntimeException("未获取到用户信息");
        }
        request.getHeaders().add("loginId", loginId);
        return chain.filter(exchange);
    }
}
