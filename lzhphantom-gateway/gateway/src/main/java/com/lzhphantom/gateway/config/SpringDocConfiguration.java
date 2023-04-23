package com.lzhphantom.gateway.config;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author lzhpahntom
 * <p>
 * swagger 3.0 展示
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SpringDocConfiguration implements InitializingBean {
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    @Override
    public void afterPropertiesSet() {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrlSet = Sets.newHashSet();
        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
        swaggerUrl.setName("lzhphantom-user");
        swaggerUrl.setUrl("/lzhphantom-user/v3/api-docs");
        swaggerUrlSet.add(swaggerUrl);
        swaggerUiConfigProperties.setUrls(swaggerUrlSet);
    }

}