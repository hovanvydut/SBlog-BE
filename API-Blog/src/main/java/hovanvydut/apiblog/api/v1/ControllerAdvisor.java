package hovanvydut.apiblog.api.v1;

import hovanvydut.apiblog.common.exception.base.MyRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MyRuntimeException.class)
    public ResponseEntity handleException(MyRuntimeException myRuntimeException, WebRequest req) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400);
        body.put("path", req.getContextPath());
        body.put("error", myRuntimeException.getErrors());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(InvalidEmailException.class)
//    public ResponseEntity handle(InvalidEmailException exception, WebRequest req) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//    }

}
