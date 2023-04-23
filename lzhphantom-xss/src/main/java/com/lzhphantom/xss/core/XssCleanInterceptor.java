package com.lzhphantom.xss.core;

import cn.hutool.core.util.ArrayUtil;
import com.lzhphantom.xss.annotation.XssCleanIgnore;
import com.lzhphantom.xss.config.LzhphantomXssProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * xss 处理拦截器
 *
 * @author lzhphantom
 */
@RequiredArgsConstructor
public class XssCleanInterceptor implements AsyncHandlerInterceptor {

    private final LzhphantomXssProperties xssProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 1. 非控制器请求直接跳出
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 2. 没有开启
        if (!xssProperties.isEnabled()) {
            return true;
        }
        // 3. 处理 XssIgnore 注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        XssCleanIgnore xssCleanIgnore = AnnotationUtils.getAnnotation(handlerMethod.getMethod(), XssCleanIgnore.class);
        if (xssCleanIgnore == null) {
            XssHolder.setEnable();
        }
        else if (ArrayUtil.isNotEmpty(xssCleanIgnore.value())) {
            XssHolder.setEnable();
            XssHolder.setXssCleanIgnore(xssCleanIgnore);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        XssHolder.remove();
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        XssHolder.remove();
    }

}
