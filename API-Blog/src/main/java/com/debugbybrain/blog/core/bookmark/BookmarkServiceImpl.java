package com.debugbybrain.blog.core.bookmark;

import com.debugbybrain.blog.common.exception.ArticleNotFoundException;
import com.debugbybrain.blog.common.exception.base.MyRuntimeException;
import com.debugbybrain.blog.common.util.SortAndPaginationUtil;
import com.debugbybrain.blog.core.article.ArticleService;
import com.debugbybrain.blog.core.article.dto.ArticleDTO;
import com.debugbybrain.blog.core.bookmark.dto.SubscriberDTO;
import com.debugbybrain.blog.core.user.UserService;
import com.debugbybrain.blog.entity.Article;
import com.debugbybrain.blog.entity.BookmarkArticle;
import com.debugbybrain.blog.entity.BookmarkArticleId;
import com.debugbybrain.blog.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@Validated
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final ArticleService articleService;
    private final UserService userService;
    private final BookmarkRepository bookmarkRepo;
    private final ModelMapper modelMapper;

    public BookmarkServiceImpl(ArticleService articleService, UserService userService, BookmarkRepository bookmarkRepo,
                               ModelMapper modelMapper) {
        this.articleService = articleService;
        this.userService = userService;
        this.bookmarkRepo = bookmarkRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<SubscriberDTO> getAllSubscribers(String articleSlug) {
        Long articleId = this.articleService.getArticleIdBySlug(articleSlug);
        return this.bookmarkRepo.findAllSubscribersOfArticle(articleId);
    }

    @Override
    public Page<ArticleDTO> getAllClippedArticlesOfUser(String username, int page, int size, String[] sort, String keyword) {
        Long userId = this.userService.getUserIdByUsername(username);
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, sort);
        Page<Article> pageArticles;

        if (keyword == null || keyword.isBlank()) {
            pageArticles = this.bookmarkRepo.findAllClippedArticlesOfUser(userId, pageable);
        } else {
            pageArticles = this.bookmarkRepo.searchByArticleTitle(keyword, userId, pageable);
        }

        List<Article> articles = pageArticles.getContent();
        List<ArticleDTO> articleDTOs = this.modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());
        return new PageImpl<>(articleDTOs, pageable, pageArticles.getTotalElements());
    }

    @Override
    public void clipArticle(String articleSlug, String subscriberUsername) {
        Long articleId = this.articleService.getArticleIdBySlug(articleSlug);
        Long subscriberId = this.userService.getUserIdByUsername(subscriberUsername);

        BookmarkArticleId bookmarkArticleId = new BookmarkArticleId().setArticleId(articleId).setUserId(subscriberId);

        this.bookmarkRepo.findById(bookmarkArticleId)
                .ifPresent(bookmarkArticle -> {
                    throw new MyRuntimeException().add("You have clipped this article before");
                });

        // TODO: Whether authors can bookmark their articles

        BookmarkArticle bookmarkArticle = new BookmarkArticle().setId(bookmarkArticleId)
                .setArticle(new Article().setId(articleId)).setUser(new User().setId(subscriberId));
        this.bookmarkRepo.save(bookmarkArticle);
    }

    @Override
    public void deleteBookmark(String articleSlug, String subscriberUsername) {
        Long articleId = this.articleService.getArticleIdBySlug(articleSlug);
        Long subscriberId = this.userService.getUserIdByUsername(subscriberUsername);

        BookmarkArticleId bookmarkArticleId = new BookmarkArticleId()
                .setArticleId(articleId).setUserId(subscriberId);
        BookmarkArticle bookmarkArticle = this.bookmarkRepo.findById(bookmarkArticleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleSlug));

        this.bookmarkRepo.delete(bookmarkArticle);
    }

}
