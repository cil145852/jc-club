package com.cjl.club.gateway.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.cjl.club.gateway.entity.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-12-10:27
 * @Description 网关全局异常处理
 */
@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        Integer code;
        String message;
        if (throwable instanceof SaTokenException) {
            code = 401;
            message = "用户无权限";
        } else {
            code = 500;
            message = "服务器异常";
        }
        Result result = Result.fail(code, message);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            try {
                return dataBufferFactory.wrap(objectMapper.writeValueAsBytes(result));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
