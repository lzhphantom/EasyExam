package com.lzhphantom.log.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author lzhphantom
 * <p>
 * 日志类型
 */
@Getter
@RequiredArgsConstructor
public enum LogTypeEnum {
    /**
     * 正常日志类型
     */
    NORMAL("0", "正常日志"),

    /**
     * 错误日志类型
     */
    ERROR("9", "错误日志");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;
}
