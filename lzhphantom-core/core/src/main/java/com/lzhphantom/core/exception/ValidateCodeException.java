package com.lzhphantom.core.exception;

/**
 * @author lzhphantom
 */
public class ValidateCodeException extends RuntimeException{
    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException() {
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
