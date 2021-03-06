package com.debugbybrain.blog.common.exception;

import com.debugbybrain.blog.common.exception.base.ResourceNotFoundException;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

public class TokenNotFoundException extends ResourceNotFoundException {

    public TokenNotFoundException() {
        super("Token not found");
    }

}
