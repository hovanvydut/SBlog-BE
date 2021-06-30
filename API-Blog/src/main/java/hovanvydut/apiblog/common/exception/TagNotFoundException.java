package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 6/30/21
 */

public class TagNotFoundException extends ResourceNotFoundException{

    public TagNotFoundException(long id) {
        super("Could not find tag with id = " + id);
    }

    public TagNotFoundException(String slug) {
        super("Could not find tag with slug = '" + slug + "'");
    }

}
