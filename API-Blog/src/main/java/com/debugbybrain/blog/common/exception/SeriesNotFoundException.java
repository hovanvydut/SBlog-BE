package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.ResourceNotFoundException;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

public class SeriesNotFoundException extends ResourceNotFoundException {

    public SeriesNotFoundException(String slug) {
        super("Could not find Series with slug = '" + slug + "'");
    }

    public SeriesNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
