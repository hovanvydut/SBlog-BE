package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.ResourceNotFoundException;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public class VerifyTokenNotFoundException extends ResourceNotFoundException {

    public VerifyTokenNotFoundException(long userId) {
        super("Could not find registration with id = " + userId);
    }

    public VerifyTokenNotFoundException(String token) {
        super("Could not find registration with token = '" + token + "'");
    }

}
