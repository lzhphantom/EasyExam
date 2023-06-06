package com.lzhphantom.codegen;

import com.lzhphantom.core.common.config.BaseRedisConfig;
import com.lzhphantom.datasource.annotation.EnableDynamicDataSource;
import com.lzhphantom.fegin.annotation.EnableLzhphantomFeignClients;
import com.lzhphantom.security.annotation.EnableLzhphantomResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * @author lzhphantom
 * 代码生成模块
 */
@EnableDynamicDataSource
@EnableLzhphantomFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableLzhphantomResourceServer
@Import({BaseRedisConfig.class})
public class CodegenApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodegenApplication.class, args);
    }

}
