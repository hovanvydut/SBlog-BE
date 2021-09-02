package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.UnsupportedException;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

public class UnsupportedFileMimeTypeException extends UnsupportedException {

    public UnsupportedFileMimeTypeException(String mimeType) {
        super(mimeType + " MIME type is not supported");
    }

    public UnsupportedFileMimeTypeException(String message, Throwable ex) {
        super(message, ex);
    }
}
