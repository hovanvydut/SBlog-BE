package hovanvydut.apiblog.core.article;

import hovanvydut.apiblog.common.enums.ArticleStatusEnum;
import hovanvydut.apiblog.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    Optional<Article> findBySlug(String slug);

    Page<Article> findByStatus(ArticleStatusEnum status, Pageable pageable);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getPublishedArticleIdBySlug(@Param("slug") String articleSlug);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getArticleIdBySlug(@Param("slug") String articleSlug);
}
