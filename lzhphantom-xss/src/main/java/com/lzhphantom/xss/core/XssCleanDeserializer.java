package com.lzhphantom.xss.core;

import com.lzhphantom.core.common.util.SpringContextHolder;
import com.lzhphantom.xss.config.LzhphantomXssProperties;
import com.lzhphantom.xss.utils.XssUtil;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * jackson xss 处理
 *
 * @author lzhphantom
 */
@Log4j2
public class XssCleanDeserializer extends XssCleanDeserializerBase {

    @Override
    public String clean(String name, String text) throws IOException {
        // 读取 xss 配置
        LzhphantomXssProperties properties = SpringContextHolder.getBean(LzhphantomXssProperties.class);
        // 读取 XssCleaner bean
        XssCleaner xssCleaner = SpringContextHolder.getBean(XssCleaner.class);
        if (xssCleaner != null) {
            String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()));
            log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
            return value;
        }
        else {
            return XssUtil.trim(text, properties.isTrimText());
        }
    }

}
