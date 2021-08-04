package hovanvydut.apiblog.common.exception;

import hovanvydut.apiblog.common.exception.base.ResourceNotFoundException;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

public class EmailNotFoundException extends ResourceNotFoundException {

    public EmailNotFoundException(String email) {
        super("Email = '" + email + "' not found");
    }
}
