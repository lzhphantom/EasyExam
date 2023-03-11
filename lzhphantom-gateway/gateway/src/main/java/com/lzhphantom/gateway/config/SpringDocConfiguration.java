package com.lzhphantom.gateway.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author lzhpahntom
 * <p>
 * swagger 3.0 展示
 */
@Configuration(proxyBeanMethods = false)
public class SpringDocConfiguration {
    @Bean
    @Lazy(value = false)
    @ConditionalOnProperty(name = "springdoc.api-docs.enabled", matchIfMissing = true)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
                                     SwaggerDocProperties swaggerProperties) {
        List<GroupedOpenApi> groups = Lists.newArrayList();
        for (String value : swaggerProperties.getServices().values()) {
            swaggerUiConfigParameters.addGroup(value);
        }
        return groups;
    }

    @Data
    @Component
    @ConfigurationProperties("swagger")
    public class SwaggerDocProperties {
        private Map<String, String> services;

        /**
         * 认证参数
         */
        private SwaggerBasic basic = new SwaggerBasic();

        @Data
        public class SwaggerBasic {

            /**
             * 是否开启 basic 认证
             */
            private Boolean enabled;

            /**
             * 用户名
             */
            private String username;

            /**
             * 密码
             */
            private String password;

        }
    }
}
