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

    @Query("SELECT a FROM Article a WHERE a.slug = :slug AND a.author.id = :authorId")
    Optional<Article> findBySlugAndAuthorId(@Param("slug") String slug, @Param("authorId") Long authorId);

    @Query("SELECT a FROM Article a INNER JOIN User u ON a.author.id = u.id WHERE a.slug = :slug AND u.username = :authorUsername")
    Optional<Article> findBySlugAndAuthorUsername(@Param("slug") String slug, @Param("authorUsername") String authorUsername);

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.tags LEFT JOIN FETCH a.category WHERE a.status = :status",
        countQuery = "SELECT COUNT(a.id) FROM Article a")
    Page<Article> findByStatus(@Param("status") ArticleStatusEnum status, Pageable pageable);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getPublishedArticleIdBySlug(@Param("slug") String articleSlug);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getArticleIdBySlug(@Param("slug") String articleSlug);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword%")
    Page<Article> search(@Param("keyword") String searchKeyword, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.status = :status AND a.title LIKE %:keyword%")
    Page<Article> searchByStatus(@Param("keyword") String searchKeyword, Pageable pageable, @Param("status") ArticleStatusEnum status);

}
