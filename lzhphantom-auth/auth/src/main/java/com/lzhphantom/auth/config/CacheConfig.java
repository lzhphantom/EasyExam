package com.lzhphantom.auth.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(){
        log.info("register cache manager");
        return new ConcurrentMapCacheManager();
    }
}
