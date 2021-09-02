package com.debugbybrain.blog.core.bookmark;

import com.debugbybrain.blog.core.article.dto.ArticleDTO;
import com.debugbybrain.blog.core.bookmark.dto.SubscriberDTO;
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
