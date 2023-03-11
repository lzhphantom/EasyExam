package com.lzhphantom.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author lzhphantom
 * 路由限流配置
 */
@Configuration(proxyBeanMethods = false)
public class RateLimiterConfiguration {
    @Bean
    public KeyResolver remoteAddrKeyResolver(){
        return exchange -> Mono
                .just(Objects.requireNonNull(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()))
                        .getAddress()
                        .getHostAddress());
    }
}
