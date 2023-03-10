package com.lzhphantom.gateway.filter;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 将cookie写入请求头
 */
@Component
@Log4j2
public class CookieToHeadersFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("token");
        if (Objects.nonNull(cookie)) {
            String token = cookie.getValue();
            ServerHttpRequest request = exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, token).build();
            return chain.filter(exchange.mutate().request(request).build());
        }
        return chain.filter(exchange);
    }
}
