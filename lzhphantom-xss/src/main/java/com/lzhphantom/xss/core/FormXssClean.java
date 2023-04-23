package com.lzhphantom.xss.core;

import cn.hutool.core.util.StrUtil;
import com.lzhphantom.xss.config.LzhphantomXssProperties;
import com.lzhphantom.xss.utils.XssUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@ControllerAdvice
@ConditionalOnProperty(prefix = LzhphantomXssProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class FormXssClean {
    private final LzhphantomXssProperties properties;

    private final XssCleaner xssCleaner;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 处理前端传来的表单字符串
        binder.registerCustomEditor(String.class, new StringPropertiesEditor(xssCleaner, properties));
    }

    @Log4j2
    @RequiredArgsConstructor
    public static class StringPropertiesEditor extends PropertyEditorSupport {

        private final XssCleaner xssCleaner;

        private final LzhphantomXssProperties properties;

        @Override
        public String getAsText() {
            Object value = getValue();
            return value != null ? value.toString() : StrUtil.EMPTY;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null) {
                setValue(null);
            }
            else if (XssHolder.isEnabled()) {
                String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()));
                setValue(value);
                log.debug("Request parameter value:{} cleaned up by mica-xss, current value is:{}.", text, value);
            }
            else {
                setValue(XssUtil.trim(text, properties.isTrimText()));
            }
        }

    }

}
