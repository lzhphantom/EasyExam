package com.lzhphantom.xss.core;

import cn.hutool.core.util.ArrayUtil;
import com.lzhphantom.xss.annotation.XssCleanIgnore;
import com.lzhphantom.xss.config.LzhphantomXssProperties;
import com.lzhphantom.xss.utils.XssUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Objects;

/**
 * jackson xss 处理
 *
 * @author lzhphantom
 */
@Log4j2
@RequiredArgsConstructor
public class JacksonXssClean extends XssCleanDeserializerBase {

    private final LzhphantomXssProperties properties;

    private final XssCleaner xssCleaner;

    @Override
    public String clean(String name, String text) throws IOException {
        if (XssHolder.isEnabled() && Objects.isNull(XssHolder.getXssCleanIgnore())) {
            String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()));
            log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
            return value;
        }
        else if (XssHolder.isEnabled() && Objects.nonNull(XssHolder.getXssCleanIgnore())) {
            XssCleanIgnore xssCleanIgnore = XssHolder.getXssCleanIgnore();
            if (ArrayUtil.contains(xssCleanIgnore.value(), name)) {
                return XssUtil.trim(text, properties.isTrimText());
            }

            String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()));
            log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
            return value;
        }
        else {
            return XssUtil.trim(text, properties.isTrimText());
        }
    }

}
