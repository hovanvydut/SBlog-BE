package hovanvydut.apiblog.core.bookmark;

import hovanvydut.apiblog.core.article.dto.ArticleDTO;
import hovanvydut.apiblog.core.bookmark.dto.SubscriberDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

public interface BookmarkService {
    List<SubscriberDTO> getAllSubscribers(String slug);
    Page<ArticleDTO> getAllClippedArticlesOfUser(String username, int page, int size, String[] sort, String keyword);
    void clipArticle(String articleSlug, String subscriberUsername);
    void deleteBookmark(String articleSlug, String subscriberUsername);
}
