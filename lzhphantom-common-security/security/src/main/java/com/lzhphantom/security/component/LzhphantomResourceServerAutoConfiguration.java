package com.lzhphantom.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

/**
 * @author lzhphantom
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class LzhphantomResourceServerAutoConfiguration {
    /**
     * 鉴权具体的实现逻辑
     * @return （#pms.xxx）
     */
    @Bean("pms")
    public PermissionService permissionService() {
        return new PermissionService();
    }

    /**
     * 请求令牌的抽取逻辑
     * @param urlProperties 对外暴露的接口列表
     * @return BearerTokenExtractor
     */
    @Bean
    public LzhphantomBearerTokenExtractor pigBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
        return new LzhphantomBearerTokenExtractor(urlProperties);
    }

    /**
     * 资源服务器异常处理
     * @param objectMapper jackson 输出对象
     * @param securityMessageSource 自定义国际化处理器
     * @return ResourceAuthExceptionEntryPoint
     */
    @Bean
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper,
                                                                           MessageSource securityMessageSource) {
        return new ResourceAuthExceptionEntryPoint(objectMapper, securityMessageSource);
    }

    /**
     * 资源服务器toke内省处理器
     * @param authorizationService token 存储实现
     * @return TokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
        return new LzhphantomCustomOpaqueTokenIntrospector(authorizationService);
    }

    /**
     * 注入 oauth2 feign token 增强
     * @param tokenResolver token获取处理器
     * @return 拦截器
     */
    @Bean
    public RequestInterceptor oauthRequestInterceptor(BearerTokenResolver tokenResolver) {
        return new LzhphantomOAuthRequestInterceptor(tokenResolver);
    }
}
