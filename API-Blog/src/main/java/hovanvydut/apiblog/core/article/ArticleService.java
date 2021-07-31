package hovanvydut.apiblog.core.article;

import hovanvydut.apiblog.core.article.dto.ArticleDTO;
import hovanvydut.apiblog.core.article.dto.CreateArticleDTO;
import hovanvydut.apiblog.core.article.dto.PublishOption;
import hovanvydut.apiblog.core.article.dto.UpdateArticleDTO;
import org.springframework.data.domain.Page;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

public interface ArticleService {
    Page<ArticleDTO> getAllPublishedArticles();
    ArticleDTO getArticle(String slug, String usernameViewer);
    ArticleDTO createNewArticle(CreateArticleDTO dto, PublishOption publishOption, String authorUsername);
    void approveArticle(String slug);
    void markArticleSpam(String slug);
    ArticleDTO updateArticle(String slug, UpdateArticleDTO dto, PublishOption publishOption, String authorUsername);
    void deleteArticle(String slug, String authorUsername);
}
