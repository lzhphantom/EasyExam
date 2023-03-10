package com.lzhphantom.gateway.security;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * 退出处理
 */
@Component
@Log4j2
public class LogoutHandler implements ServerLogoutHandler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {

        HttpCookie cookie = exchange.getExchange().getRequest().getCookies().getFirst("token");
        if (Objects.nonNull(cookie)) {
//            Map<String ,Object> = JWTUtil
            Map<String, Object> userMap = Maps.newHashMap();
            redisTemplate.delete((String) userMap.get("username"));
        }

        return Mono.empty();
    }
}
