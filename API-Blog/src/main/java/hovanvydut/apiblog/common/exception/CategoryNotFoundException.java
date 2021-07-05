package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

public class CategoryNotFoundException extends ResourceNotFoundException{
    public CategoryNotFoundException(long id) {
        super("Could not find Category with id = " + id);
    }

    public CategoryNotFoundException(String slug) {
        super("Could not find Category with slug = '" + slug + "'");
    }
}
