package hovanvydut.apiblog.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

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
