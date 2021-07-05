package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public class UserRegistrationNotFoundException extends ResourceNotFoundException{

    public UserRegistrationNotFoundException() {
        super("Could not find UserRegistration");
    }

    public UserRegistrationNotFoundException(long id) {
        super("Could not find UserRegistration with id = " + id);
    }

    public UserRegistrationNotFoundException(String email, String username) {
        super(generateMsg(email, username));
    }


    public UserRegistrationNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    private static String generateMsg(String email, String username) {
        String msg = " with ";

        if (email != null && !email.isBlank()) {
            msg += "email = " + email + ", ";
        }

        if (username != null & !username.isBlank()) {
            msg += "username = " + username + ", ";
        }

        return msg;
    }
}
