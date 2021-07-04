package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public class UserRegistrationTokenNotFoundException extends ResourceNotFoundException{

    public UserRegistrationTokenNotFoundException(String token) {
        super("Could not find registration with token = '" + token + "'");
    }

}
