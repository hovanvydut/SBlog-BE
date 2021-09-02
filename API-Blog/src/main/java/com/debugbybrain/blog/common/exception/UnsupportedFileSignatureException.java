package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.UnsupportedException;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

public class UnsupportedFileSignatureException extends UnsupportedException {

    public UnsupportedFileSignatureException() {
        super("Signature file is not supported.");
    }

    public UnsupportedFileSignatureException(String message) {
        super(message);
    }

    public UnsupportedFileSignatureException(String message, Throwable ex) {
        super(message, ex);
    }

}
