package com.lzhphantom.security.util;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @author lzhphantom
 * @description ScopeException 异常信息
 */
public class ScopeException extends OAuth2AuthenticationException {

    public ScopeException(String msg) {
        super(new OAuth2Error(msg), msg);
    }

    public ScopeException(String msg, Throwable cause) {
        super(new OAuth2Error(msg), cause);
    }
}
