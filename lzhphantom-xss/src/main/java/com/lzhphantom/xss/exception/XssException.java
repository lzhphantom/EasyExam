package com.lzhphantom.xss.exception;

/**
 * xss 异常，校验模式抛出
 *
 * @author lzhphantom
 */
public interface XssException {
    /**
     * 输入的数据
     * @return 数据
     */
    String getInput();

    /**
     * 获取异常的消息
     * @return 消息
     */
    String getMessage();
}
