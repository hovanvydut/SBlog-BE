package com.debugbybrain.blog.core.article;

import com.debugbybrain.blog.core.article.dto.*;
import com.debugbybrain.blog.entity.enums.ArticleType;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

public interface ArticleService {
    Page<ArticleDTO> getAllArticlesOrSeries(int page, int size, String[] sort, String searchKeyword, ArticleType type);
    Page<ArticleDTO> getAllPublishedArticlesOrSeries(Optional<String> username, ArticleType type, int page, int size,
                                                     String[] sort, String searchKeyword);
    ArticleDTO getArticle(String slug, String usernameViewer);
    SeriesDTO getSeries(String slug, String usernameViewer);
    ArticleDTO createNewArticle(@Valid CreateArticleDTO dto, PublishOption publishOption, String authorUsername, boolean isSeries);
    void approveArticle(String slug);
    void markArticleSpam(String slug);
    ArticleDTO updateArticle(String slug, UpdateArticleDTO dto, PublishOption publishOption, String authorUsername, boolean isSeries);
    void deleteArticle(String slug, String authorUsername);
    Long getArticleIdBySlug(String slug);
}
