package com.lzhphantom.auth;

import com.lzhphantom.core.common.config.BaseRedisConfig;
import com.lzhphantom.core.common.config.CacheConfig;
import com.lzhphantom.core.common.config.WebMvcConfiguration;
import com.lzhphantom.fegin.annotation.EnableLzhphantomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


@EnableDiscoveryClient
@SpringBootApplication
@EnableLzhphantomFeignClients
@Import({BaseRedisConfig.class, WebMvcConfiguration.class, CacheConfig.class})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
