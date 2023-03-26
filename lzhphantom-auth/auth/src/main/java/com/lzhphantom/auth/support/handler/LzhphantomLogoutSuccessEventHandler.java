package com.lzhphantom.auth.support.handler;

import com.lzhphantom.core.common.util.LzhphantomWebUtils;
import com.lzhphantom.core.common.util.SpringContextHolder;
import com.lzhphantom.log.event.LzhphantomLogEvent;
import com.lzhphantom.log.utils.LogUtils;
import com.lzhphantom.user.login.entity.SystemLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @author lzhphantom
 * <p>
 * 事件机制处理退出相关
 */
@Log4j2
@Component
public class LzhphantomLogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {
    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            handle(authentication);
        }
    }

    /**
     * 处理退出成功方法
     * <p>
     * 获取到登录的authentication 对象
     *
     * @param authentication 登录对象
     */
    private void handle(Authentication authentication) {
        log.info("用户：{} 退出成功", authentication.getPrincipal());
        SystemLog logVo = LogUtils.getSystemLog();
        logVo.setTitle("退出成功");
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Long endTime = System.currentTimeMillis();
        logVo.setTime(endTime - startTime);

        // 设置对应的token
        LzhphantomWebUtils.getRequest().ifPresent(request -> logVo.setParams(request.getHeader(HttpHeaders.AUTHORIZATION)));

        // 这边设置ServiceId
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            logVo.setServiceId(authentication.getCredentials().toString());
        }
        logVo.setCreateBy(authentication.getName());
        logVo.setUpdateBy(authentication.getName());
        SpringContextHolder.publishEvent(new LzhphantomLogEvent(logVo));
    }
}
