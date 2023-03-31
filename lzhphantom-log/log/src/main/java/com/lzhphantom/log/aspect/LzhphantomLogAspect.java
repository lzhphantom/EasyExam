package com.lzhphantom.log.aspect;

import cn.hutool.core.util.StrUtil;
import com.lzhphantom.core.common.util.SpringContextHolder;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.log.event.LzhphantomLogEvent;
import com.lzhphantom.log.utils.LogTypeEnum;
import com.lzhphantom.log.utils.LogUtils;
import com.lzhphantom.user.login.entity.SystemLog;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;

@Aspect
@Log4j2
public class LzhphantomLogAspect {
    @Around("@annotation(lzhphantomLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, LzhphantomLog lzhphantomLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        String value = lzhphantomLog.value();
        String expression = lzhphantomLog.expression();
        // 当前表达式存在 SPEL，会覆盖 value 的值
        if (StrUtil.isNotBlank(expression)) {
            // 解析SPEL
            MethodSignature signature = (MethodSignature) point.getSignature();
            EvaluationContext context = LogUtils.getContext(point.getArgs(), signature.getMethod());
            try {
                value = LogUtils.getValue(context, expression, String.class);
            } catch (Exception e) {
                // SPEL 表达式异常，获取 value 的值
                log.error("@LzhphantomLog 解析SPEL {} 异常", expression);
            }
        }

        SystemLog logVo = LogUtils.getSystemLog();
        logVo.setTitle(value);

        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj;

        try {
            obj = point.proceed();
        } catch (Exception e) {
            logVo.setType(LogTypeEnum.ERROR.getType());
            logVo.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            logVo.setTime(endTime - startTime);
            SpringContextHolder.publishEvent(new LzhphantomLogEvent(logVo));
        }

        return obj;
    }
}
