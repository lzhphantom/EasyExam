package com.lzhphantom.core.common.util;

import com.lzhphantom.core.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LzhphantomResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private T data;
    public static <T> LzhphantomResult<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> LzhphantomResult<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> LzhphantomResult<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> LzhphantomResult<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> LzhphantomResult<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> LzhphantomResult<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> LzhphantomResult<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    public static <T> LzhphantomResult<T> restResult(T data, int code, String msg) {
        LzhphantomResult<T> apiResult = new LzhphantomResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
