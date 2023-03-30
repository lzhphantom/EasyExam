package com.lzhphantom.userimpl;

import annotation.EnableLzhphantomDoc;
import com.lzhphantom.fegin.annotation.EnableLzhphantomFeignClients;
import com.lzhphantom.security.annotation.EnableLzhphantomResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableLzhphantomDoc
@EnableLzhphantomFeignClients
@EnableDiscoveryClient
@EnableLzhphantomResourceServer
public class UserImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserImplApplication.class, args);
    }

}
