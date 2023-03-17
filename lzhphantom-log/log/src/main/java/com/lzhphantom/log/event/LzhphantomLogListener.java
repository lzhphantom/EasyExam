package com.lzhphantom.log.event;

import com.lzhphantom.user.feign.RemoteLogService;
import com.lzhphantom.user.login.entity.SystemLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author lzhphantom
 * 异步监听日志事件
 */
@Log4j2
@RequiredArgsConstructor
public class LzhphantomLogListener {
    private final RemoteLogService remoteLogService;

    @Async
    @Order
    @EventListener(LzhphantomLogEvent.class)
    public void saveSysLog(LzhphantomLogEvent event) {
        SystemLog systemLog = (SystemLog) event.getSource();
        remoteLogService.saveLog(systemLog);
    }
}
