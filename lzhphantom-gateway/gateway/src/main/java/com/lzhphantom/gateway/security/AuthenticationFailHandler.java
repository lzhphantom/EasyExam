package com.lzhphantom.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 认证失败处理
 */
@Component
@Log4j2
public class AuthenticationFailHandler implements ServerAuthenticationFailureHandler {

    @SneakyThrows
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        Map<String, String> map = Maps.newHashMap();
        map.put("code", "000400");
        map.put("message", exception.getMessage());
        log.error("access forbidden path={}", webFilterExchange.getExchange().getRequest().getPath());
        ObjectMapper objectMapper = new ObjectMapper();
        DataBuffer dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(map));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
