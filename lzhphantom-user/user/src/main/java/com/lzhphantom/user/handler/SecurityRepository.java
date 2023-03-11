package com.lzhphantom.user.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 用户信息上文存储
 */
@Component
@Log4j2
public class SecurityRepository implements ServerSecurityContextRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info(token);
        if (Objects.nonNull(token)) {
//            Map<String ,Object> userMap = JWTUtil
            Map<String, Object> userMap = Maps.newHashMap();
            String result = (String) redisTemplate.opsForValue().get(userMap.get("username"));
            if (Objects.isNull(result) || !StringUtils.equals(result, token)) {
                return Mono.empty();
            }
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            Collection<SimpleGrantedAuthority> authorities = Lists.newArrayList();
            log.info(userMap.get("role").toString());
            authorities.add(new SimpleGrantedAuthority((String) userMap.get("role")));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, null, authorities);
            emptyContext.setAuthentication(authenticationToken);
            return Mono.just(emptyContext);
        }

        return Mono.empty();
    }
}
