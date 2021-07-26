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
    public Page<ArticleDTO> getAllPublishedArticles();
    public ArticleDTO getArticle(String slug, String usernameViewer);
    public ArticleDTO createNewArticle(CreateArticleDTO dto, PublishOption publishOption, String authorUsername);
    public void approveArticle(String slug);
    public void markArticleSpam(String slug);
    public ArticleDTO updateArticle(String slug, UpdateArticleDTO dto, PublishOption publishOption, String authorUsername);
}
