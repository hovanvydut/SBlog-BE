package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.ResourceNotFoundException;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
