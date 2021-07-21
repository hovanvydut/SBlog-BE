package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class CommentNotFoundException extends ResourceNotFoundException {

    public CommentNotFoundException(long id) {
        super("Could not find Comment with id = " + id);
    }

}
