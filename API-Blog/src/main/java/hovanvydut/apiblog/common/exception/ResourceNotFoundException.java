package hovanvydut.apiblog.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author hovanvydut
 * Created on 6/30/21
 */

@ResponseStatus(NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
