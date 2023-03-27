package com.lzhphantom.fegin.sentinel.handle;

import com.alibaba.csp.sentinel.Tracer;
import com.lzhphantom.core.common.util.LzhphantomResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * <p>
 * 全局异常处理器结合sentinel 全局异常处理器不能作用在 oauth server https://gitee.com/log4j/pig/issues/I1M2TJ
 * </p>
 *
 * @author lzhphantom
 */
@Log4j2
@Order(10000)
@RestControllerAdvice
@ConditionalOnExpression("!'${security.oauth2.client.clientId}'.isEmpty()")
public class GlobalBizExceptionHandler {

    /**
     * 全局异常.
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public LzhphantomResult handleGlobalException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);

        // 业务异常交由 sentinel 记录
        Tracer.trace(e);
        return LzhphantomResult.failed(e.getLocalizedMessage());
    }

    /**
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     * @see Assert#hasLength(String, String)
     * @see Assert#hasText(String, String)
     * @see Assert#isTrue(boolean, String)
     * @see Assert#isNull(Object, String)
     * @see Assert#notNull(Object, String)
     * @param exception 参数校验异常
     * @return API返回结果对象包装后的错误输出结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public LzhphantomResult handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("非法参数,ex = {}", exception.getMessage(), exception);
        return LzhphantomResult.failed(exception.getMessage());
    }

    /**
     * AccessDeniedException
     * @param e the e
     * @return R
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public LzhphantomResult handleAccessDeniedException(AccessDeniedException e) {
        String msg = SpringSecurityMessageSource.getAccessor().getMessage("AbstractAccessDecisionManager.accessDenied",
                e.getMessage());
        log.warn("拒绝授权异常信息 ex={}", msg);
        return LzhphantomResult.failed(e.getLocalizedMessage());
    }

    /**
     * validation Exception
     * @param exception
     * @return R
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LzhphantomResult handleBodyValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return LzhphantomResult.failed(String.format("%s %s", fieldErrors.get(0).getField(), fieldErrors.get(0).getDefaultMessage()));
    }

    /**
     * validation Exception (以form-data形式传参)
     * @param exception
     * @return R
     */
    @ExceptionHandler({ BindException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LzhphantomResult bindExceptionHandler(BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return LzhphantomResult.failed(fieldErrors.get(0).getDefaultMessage());
    }

}
