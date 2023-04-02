package com.lzhphantom.security.component;

import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.lzhphantom.security.annotation.Inner;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author lzhphantom
 * <p>
 * 资源服务器对外直接暴露URL,如果设置contex-path 要特殊处理
 */
@Log4j2
@ConfigurationProperties(prefix = "security.oauth2.ignore")
@Data
public class PermitAllUrlProperties implements InitializingBean {
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private static final String[] DEFAULT_IGNORE_URLS = new String[] { "/actuator/**", "/error", "/v3/api-docs","/test" };
    private List<String> urls = Lists.newArrayList();

    @Override
    public void afterPropertiesSet() {
        urls.addAll(Arrays.asList(DEFAULT_IGNORE_URLS));
        RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(info->{
            HandlerMethod handlerMethod = map.get(info);
            // 获取方法上边的注解 替代path variable 为 *
            Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
            Optional.ofNullable(method)
                    .ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
                            .getPatternValues()
                            .forEach(url->urls.add(ReUtil.replaceAll(url,PATTERN,"*"))));
            // 获取类上边的注解, 替代path variable 为 *
            Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
            Optional.ofNullable(controller)
                    .ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
                            .getPatternValues()
                            .forEach(url->urls.add(ReUtil.replaceAll(url,PATTERN,"*"))));
        });
    }
}
