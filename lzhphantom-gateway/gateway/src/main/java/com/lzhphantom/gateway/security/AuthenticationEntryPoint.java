package com.lzhphantom.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 接口认证入口
 */
@Component
@Log4j2
public class AuthenticationEntryPoint extends HttpBasicServerAuthenticationEntryPoint {
    @SneakyThrows
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", "00401");
        map.put("message", "未登录");
        ObjectMapper objectMapper = new ObjectMapper();
        DataBuffer dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(map));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
