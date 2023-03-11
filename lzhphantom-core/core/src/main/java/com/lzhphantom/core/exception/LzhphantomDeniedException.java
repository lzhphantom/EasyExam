package com.lzhphantom.core.exception;

import lombok.NoArgsConstructor;

/**
 * @author lzhphantom
 */
@NoArgsConstructor
public class LzhphantomDeniedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LzhphantomDeniedException(String message) {
        super(message);
    }

    public LzhphantomDeniedException(Throwable cause) {
        super(cause);
    }

    public LzhphantomDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LzhphantomDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
