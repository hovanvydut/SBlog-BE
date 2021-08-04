package hovanvydut.apiblog.common.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@ResponseStatus(BAD_REQUEST)
public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException() {
        super("Token is expired");
    }

}
