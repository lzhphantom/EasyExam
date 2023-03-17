package com.lzhphantom.log.event;

import com.lzhphantom.user.login.entity.SystemLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author lzhphantom
 * 系统日志事件
 */
public class LzhphantomLogEvent extends ApplicationEvent {
    public LzhphantomLogEvent(SystemLog source) {
        super(source);
    }
}
