package com.lzhphantom.userimpl;

import com.lzhphantom.core.common.config.BaseRedisConfig;
import com.lzhphantom.core.common.config.CacheConfig;
import com.lzhphantom.fegin.annotation.EnableLzhphantomFeignClients;
import com.lzhphantom.security.annotation.EnableLzhphantomResourceServer;
import com.lzhphantom.swagger.annotation.EnableLzhphantomDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableLzhphantomDoc
@EnableLzhphantomFeignClients
@EnableDiscoveryClient
@EnableLzhphantomResourceServer
@Import({CacheConfig.class, BaseRedisConfig.class})
public class UserImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserImplApplication.class, args);
    }

}
