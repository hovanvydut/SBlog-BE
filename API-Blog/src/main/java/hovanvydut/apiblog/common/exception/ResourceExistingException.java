package hovanvydut.apiblog.common.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

@ResponseStatus(CONFLICT)
public class ResourceExistingException extends RuntimeException{

    public ResourceExistingException(String msg) {
        super(msg);
    }

    public ResourceExistingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
