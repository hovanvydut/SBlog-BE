package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public class VerifyTokenNotFoundException extends ResourceNotFoundException{

    public VerifyTokenNotFoundException(long userId) {
        super("Could not find registration with id = " + userId);
    }

    public VerifyTokenNotFoundException(String token) {
        super("Could not find registration with token = '" + token + "'");
    }

}
