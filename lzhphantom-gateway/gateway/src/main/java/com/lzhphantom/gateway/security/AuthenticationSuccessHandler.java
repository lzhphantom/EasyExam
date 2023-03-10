package com.lzhphantom.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 认证成功处理
 */
@Component
@Log4j2
public class AuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

    @Value("${login.timeout:3600}")
    private int timeout;
    private final int rememberMe = 180;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @SneakyThrows

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();

        HttpHeaders headers = response.getHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.setCacheControl("no-store, no-cache, must-revalidate, max-age=0");

        Map<String, String> map = Maps.newHashMap();
        String rememberMe = exchange.getRequest().getHeaders().getFirst("Remember-me");
        ObjectMapper objectMapper = new ObjectMapper();
        List<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities().stream().toList();
        Map<String, String> load = Maps.newHashMap();
        load.put("username", authentication.getName());
        load.put("role", grantedAuthorities.get(0).getAuthority());
        String token = null;
        log.info(authentication.toString());
        if (Objects.isNull(rememberMe)) {
//            token=JWTUtil.get
            response.addCookie(ResponseCookie.from("token", token).path("/").build());
            redisTemplate.opsForValue().set(authentication.getName(), token, 1, TimeUnit.DAYS);
        } else {
            response.addCookie(ResponseCookie.from("token", token).maxAge(Duration.ofDays(this.rememberMe)).path("/").build());
            redisTemplate.opsForValue().set(authentication.getName(), token, this.rememberMe, TimeUnit.SECONDS);
        }
        map.put("code", "000220");
        map.put("message", "登录成功");
        map.put("token", token);
        DataBuffer dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(map));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
