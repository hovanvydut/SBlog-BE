package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class ReplyNotFoundException extends ResourceNotFoundException {

    public ReplyNotFoundException(long id) {
        super("Could not find Comment with id = " + id);
    }

}
