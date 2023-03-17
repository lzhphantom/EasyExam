package com.lzhphantom.log;

import com.lzhphantom.log.aspect.LzhphantomLogAspect;
import com.lzhphantom.log.event.LzhphantomLogListener;
import com.lzhphantom.user.feign.RemoteLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lzhphantom
 * 日志自动配置
 */
@Configuration(proxyBeanMethods = false)
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {
    @Bean
    public LzhphantomLogListener sysLogListener(RemoteLogService remoteLogService) {
        return new LzhphantomLogListener(remoteLogService);
    }

    @Bean
    public LzhphantomLogAspect sysLogAspect() {
        return new LzhphantomLogAspect();
    }
}
