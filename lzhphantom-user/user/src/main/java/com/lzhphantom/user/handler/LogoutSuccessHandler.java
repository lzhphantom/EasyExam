package com.lzhphantom.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 退出成功处理
 */
@Component
@Log4j2
public class LogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @SneakyThrows
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerHttpResponse response = exchange.getExchange().getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.setCacheControl("no-store, no-cache, must-revalidate, max-age=0");
        Map<String, String> map = Maps.newHashMap();
        response.addCookie(ResponseCookie.from("token", "logout").maxAge(0).path("/").build());
        map.put("code", "000220");
        map.put("message", "退出成功");
        ObjectMapper objectMapper = new ObjectMapper();
        DataBuffer dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(map));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
