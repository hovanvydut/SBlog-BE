package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
