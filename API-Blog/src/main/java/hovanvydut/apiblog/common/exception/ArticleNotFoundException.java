package hovanvydut.apiblog.common.exception;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

public class ArticleNotFoundException extends ResourceNotFoundException {

    public ArticleNotFoundException(String slug) {
        super("Could not find Article with slug = '" + slug + "'");
    }

    public ArticleNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
