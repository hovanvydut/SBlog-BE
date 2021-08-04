package hovanvydut.apiblog.common.exception.base;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@ResponseStatus(BAD_REQUEST)
public class MyRuntimeException extends RuntimeException{
    private final List<MyError> errors;

    public MyRuntimeException() {
        super();
        this.errors = new ArrayList<>();
    }

    public MyRuntimeException(List<MyError> errors) {
        super();
        this.errors = errors;
    }

    public void add(MyError error) {
        this.errors.add(error);
    }

    public MyRuntimeException add(String msg) {
        this.errors.add(new MyError().setMessage(msg));
        return this;
    }

    public List<MyError> getErrors() {
        return this.errors;
    }
}
