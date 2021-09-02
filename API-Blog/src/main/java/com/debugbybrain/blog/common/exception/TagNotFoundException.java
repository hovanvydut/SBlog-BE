package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.ResourceNotFoundException;

/**
 * @author hovanvydut
 * Created on 6/30/21
 */

public class TagNotFoundException extends ResourceNotFoundException {

    public TagNotFoundException(long id) {
        super("Could not find Tag with id = " + id);
    }

    public TagNotFoundException(String slug) {
        super("Could not find Tag with slug = '" + slug + "'");
    }

}
