package com.lzhphantom.security.util;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @author lzhphantom
 * @description OAuthClientException 异常信息
 */
public class OAuthClientException extends OAuth2AuthenticationException {

    public OAuthClientException(String msg) {
        super(new OAuth2Error(msg), msg);
    }

    public OAuthClientException(String msg, Throwable cause) {
        super(new OAuth2Error(msg), cause);
    }
}
