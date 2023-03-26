package com.lzhphantom.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzhphantom.gateway.filter.LzhphantomRequestGlobalFilter;
import com.lzhphantom.gateway.filter.PasswordDecoderFilter;
import com.lzhphantom.gateway.filter.ValidateCodeGatewayFilter;
import com.lzhphantom.gateway.handler.GlobalExceptionHandler;
import com.lzhphantom.gateway.handler.ImageCodeHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GatewayConfigProperties.class)
public class GatewayConfiguration {

    @Bean
    public PasswordDecoderFilter passwordDecoderFilter(GatewayConfigProperties configProperties){
        return new PasswordDecoderFilter(configProperties);
    }

    @Bean
    public LzhphantomRequestGlobalFilter lzhphantomRequestGlobalFilter(){
        return new LzhphantomRequestGlobalFilter();
    }


    @Bean
    public ValidateCodeGatewayFilter validateCodeGatewayFilter(GatewayConfigProperties configProperties,
                                                               ObjectMapper objectMapper, RedisTemplate redisTemplate){
        return new ValidateCodeGatewayFilter(configProperties, objectMapper, redisTemplate);
    }
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
        return new GlobalExceptionHandler(objectMapper);
    }

    @Bean
    public ImageCodeHandler imageCodeHandler(RedisTemplate redisTemplate) {
        return new ImageCodeHandler(redisTemplate);
    }
}
