package com.debugbybrain.blog.common.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedException extends RuntimeException {

    public UnsupportedException(String message) {
        super(message);
    }

    public UnsupportedException(String message, Throwable ex) {
        super(message, ex);
    }

}
