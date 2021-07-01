package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

public class TagExistingException extends ResourceExistingException{

    public TagExistingException(String name) {
        super("Tag with name = '" + name + "' is existing");
    }

}
