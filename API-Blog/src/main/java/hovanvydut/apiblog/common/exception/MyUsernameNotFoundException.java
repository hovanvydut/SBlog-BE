package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/11/21
 */

public class MyUsernameNotFoundException extends ResourceNotFoundException{
    public MyUsernameNotFoundException(String username) {
        super("Could not found User with username = " + username);
    }
}
