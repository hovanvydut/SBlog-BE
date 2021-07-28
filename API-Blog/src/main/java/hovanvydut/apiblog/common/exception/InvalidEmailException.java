package hovanvydut.apiblog.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintDefinitionException;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends ConstraintDefinitionException {
}
