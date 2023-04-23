package com.lzhphantom.xss.core;

import cn.hutool.core.util.CharsetUtil;
import com.lzhphantom.xss.config.LzhphantomXssProperties;
import com.lzhphantom.xss.utils.XssUtil;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.springframework.web.util.HtmlUtils;

/**
 * 默认的 xss 清理器
 *
 * @author lzhphantom
 */
public class DefaultXssCleaner implements XssCleaner{
    private final LzhphantomXssProperties properties;

    public DefaultXssCleaner(LzhphantomXssProperties properties) {
        this.properties = properties;
    }

    private static Document.OutputSettings getOutputSettings(LzhphantomXssProperties properties) {
        return new Document.OutputSettings()
                // 2. 转义，没找到关闭的方法，目前这个规则最少
                .escapeMode(Entities.EscapeMode.xhtml)
                // 3. 保留换行
                .prettyPrint(properties.isPrettyPrint());
    }

    @Override
    public String clean(String bodyHtml, XssType type) {
        // 1. 为空直接返回
        if (StringUtil.isBlank(bodyHtml)) {
            return bodyHtml;
        }
        LzhphantomXssProperties.Mode mode = properties.getMode();
        if (LzhphantomXssProperties.Mode.escape == mode) {
            // html 转义
            return HtmlUtils.htmlEscape(bodyHtml, CharsetUtil.UTF_8);
        }
        else if (LzhphantomXssProperties.Mode.validate == mode) {
            // 校验
            if (Jsoup.isValid(bodyHtml, XssUtil.WHITE_LIST)) {
                return bodyHtml;
            }
            throw type.getXssException(bodyHtml, "Xss validate fail, input value:" + bodyHtml);
        }
        else {
            // 4. 清理后的 html
            String escapedHtml = Jsoup.clean(bodyHtml, "", XssUtil.WHITE_LIST, getOutputSettings(properties));
            if (properties.isEnableEscape()) {
                return escapedHtml;
            }
            // 5. 反转义
            return Entities.unescape(escapedHtml);
        }
    }
}
