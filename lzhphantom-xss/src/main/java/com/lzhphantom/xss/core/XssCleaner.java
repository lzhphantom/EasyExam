package com.lzhphantom.xss.core;

import com.lzhphantom.xss.utils.XssUtil;
import org.jsoup.Jsoup;

/**
 * xss 清理器
 *
 * @author lzhphantom
 */
public interface XssCleaner {
    /**
     * 清理 html
     * @param html html
     * @return 清理后的数据
     */
    default String clean(String html) {
        return clean(html, XssType.FORM);
    }

    /**
     * 清理 html
     * @param html html
     * @param type XssType
     * @return 清理后的数据
     */
    String clean(String html, XssType type);

    /**
     * 判断输入是否安全
     * @param html html
     * @return 是否安全
     */
    default boolean isValid(String html) {
        return Jsoup.isValid(html, XssUtil.WHITE_LIST);
    }
}
