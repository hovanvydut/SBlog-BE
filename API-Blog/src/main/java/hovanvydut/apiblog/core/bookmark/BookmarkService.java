package hovanvydut.apiblog.core.bookmark;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

public interface BookmarkService {
    public void clipArticle(String articleSlug, String subscriberUsername);

    public void deleteBookmark(String articleSlug, String subscriberUsername);
}
