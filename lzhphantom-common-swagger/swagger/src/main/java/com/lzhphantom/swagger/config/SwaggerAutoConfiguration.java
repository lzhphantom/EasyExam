package com.lzhphantom.swagger.config;

import com.google.common.collect.Lists;
import com.lzhphantom.swagger.support.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import java.util.List;


/**
 * swagger配置
 *
 * <p>
 * 禁用方法1：使用注解@Profile({"dev","test"})
 * <p>
 * 表示在开发或测试环境开启，而在生产关闭。（推荐使用） 禁用方法2：使用注解@ConditionalOnProperty(name = "swagger.enable",
 * <p>
 * havingValue = "true") 然后在测试配置或者开发配置中添加swagger.enable=true即可开启，生产环境不填则默认关闭Swagger.
 * </p>
 *
 * @author lzhphantom
 */
@RequiredArgsConstructor
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
@ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class SwaggerAutoConfiguration {

    private final SwaggerProperties swaggerProperties;

    private final ServiceInstance serviceInstance;

    @Bean
    public OpenAPI springOpenAPI() {
        OpenAPI openAPI = new OpenAPI().info(new Info().title(swaggerProperties.getTitle()));
        // oauth2.0 password
        openAPI.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());
        // servers
        List<Server> serverList = Lists.newArrayList();
        String path = swaggerProperties.getServices().get(serviceInstance.getServiceId());
        serverList.add(new Server().url(swaggerProperties.getGateway() + "/" + path));
        openAPI.servers(serverList);
        return openAPI;
    }

    private SecurityScheme securityScheme() {
        OAuthFlow clientCredential = new OAuthFlow();
        clientCredential.setTokenUrl(swaggerProperties.getTokenUrl());
        clientCredential.setScopes(new Scopes().addString(swaggerProperties.getScope(), swaggerProperties.getScope()));
        OAuthFlows authFlows = new OAuthFlows();
        authFlows.password(clientCredential);
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setFlows(authFlows);
        return securityScheme;
    }

}
