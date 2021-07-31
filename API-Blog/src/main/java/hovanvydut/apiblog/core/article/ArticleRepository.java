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

    @Query("SELECT a from Article a WHERE a.slug = :slug AND a.author.id = :authorId")
    Optional<Article> findBySlugAndAuthorId(@Param("slug") String slug, @Param("authorId") Long authorId);

    @Query("SELECT a from Article a INNER JOIN User u ON a.author.id = u.id WHERE a.slug = :slug AND u.username = :authorUsername")
    Optional<Article> findBySlugAndAuthorUsername(@Param("slug") String slug, @Param("authorUsername") String authorUsername);

    Page<Article> findByStatus(ArticleStatusEnum status, Pageable pageable);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getPublishedArticleIdBySlug(@Param("slug") String articleSlug);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getArticleIdBySlug(@Param("slug") String articleSlug);
}
