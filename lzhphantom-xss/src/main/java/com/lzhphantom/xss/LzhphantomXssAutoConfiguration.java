package com.lzhphantom.xss;

import com.lzhphantom.xss.config.LzhphantomXssProperties;
import com.lzhphantom.xss.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * jackson xss 配置
 *
 * @author lzhphantom
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(LzhphantomXssProperties.class)
@ConditionalOnProperty(prefix = LzhphantomXssProperties.PREFIX, name = "enabled",
        havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LzhphantomXssAutoConfiguration implements WebMvcConfigurer {
    private final LzhphantomXssProperties xssProperties;

    @Bean
    @ConditionalOnMissingBean
    public XssCleaner xssCleaner(LzhphantomXssProperties properties) {
        return new DefaultXssCleaner(properties);
    }

    @Bean
    public FormXssClean formXssClean(LzhphantomXssProperties properties,
                                     XssCleaner xssCleaner) {
        return new FormXssClean(properties, xssCleaner);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(
            LzhphantomXssProperties properties, XssCleaner xssCleaner) {
        return builder -> builder.deserializerByType(String.class, new JacksonXssClean(properties, xssCleaner));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = xssProperties.getPathPatterns();
        if (patterns.isEmpty()) {
            patterns.add("/**");
        }
        XssCleanInterceptor interceptor = new XssCleanInterceptor(xssProperties);
        registry.addInterceptor(interceptor).addPathPatterns(patterns)
                .excludePathPatterns(xssProperties.getPathExcludePatterns()).order(Ordered.LOWEST_PRECEDENCE);
    }
}
