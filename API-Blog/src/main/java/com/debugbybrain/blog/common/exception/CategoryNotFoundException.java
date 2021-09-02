package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.ResourceNotFoundException;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(long id) {
        super("Could not find Category with id = " + id);
    }

    public CategoryNotFoundException(String slug) {
        super("Could not find Category with slug = '" + slug + "'");
    }
}
