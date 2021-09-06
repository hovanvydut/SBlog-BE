package com.debugbybrain.blog.core.article;

import com.debugbybrain.blog.entity.Article;
import com.debugbybrain.blog.entity.enums.ArticleStatusEnum;
import com.debugbybrain.blog.entity.enums.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.category LEFT JOIN FETCH a.tags WHERE a.type = ?1",
            countQuery = "SELECT COUNT(a.id) FROM Article a WHERE a.type = ?1")
    Page<Article> getAll(ArticleType type, Pageable pageable);

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.category LEFT JOIN FETCH a.tags " +
            "WHERE a.slug = ?1")
    Optional<Article> findBySlug(String slug);

    @Query(value = "SELECT a.id FROM Article a WHERE a.slug = ?1")
    Optional<Long> getIdBySlug(String slug);

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.category LEFT JOIN FETCH a.tags " +
            "LEFT JOIN FETCH a.articles WHERE a.type = com.debugbybrain.blog.entity.enums.ArticleType.SERIES AND a.slug = ?1")
    Optional<Article> getSeriesBySlug(String slug);

    @Query("SELECT a FROM Article a WHERE a.slug = :slug AND a.author.id = :authorId")
    Optional<Article> findBySlugAndAuthorId(@Param("slug") String slug, @Param("authorId") Long authorId);

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.category LEFT JOIN FETCH a.tags INNER JOIN User u ON a.author.id = u.id WHERE a.slug = :slug AND u.username = :authorUsername")
    Optional<Article> findBySlugAndAuthorUsernameEager(@Param("slug") String slug, @Param("authorUsername") String authorUsername);

    @Query("SELECT a.id FROM Article a INNER JOIN User u ON a.author.id = u.id WHERE a.slug = :slug AND u.username = :authorUsername")
    Optional<Long> getIdBySlugAndAuthorUsername(@Param("slug") String slug, @Param("authorUsername") String authorUsername);

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.tags LEFT JOIN FETCH a.category " +
            "WHERE a.type = :type AND a.status = :status",
        countQuery = "SELECT COUNT(a.id) FROM Article a WHERE a.type = :type AND a.status = :status")
    Page<Article> findByStatus(@Param("type") ArticleType type, @Param("status") ArticleStatusEnum status, Pageable pageable);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getPublishedArticleIdBySlug(@Param("slug") String articleSlug);

    @Query("SELECT a.id FROM Article a WHERE a.slug = :slug")
    Optional<Long> getArticleIdBySlug(@Param("slug") String articleSlug);

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.category LEFT JOIN FETCH a.tags " +
            "WHERE a.type = :type AND a.title LIKE %:keyword%",
            countQuery = "SELECT COUNT(a.id) FROM Article a WHERE a.type = :type AND a.title LIKE %:keyword%")
    Page<Article> search(@Param("type") ArticleType type, @Param("keyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT a FROM Article a WHERE a.type = :type AND a.status = :status AND a.title LIKE %:keyword%",
            countQuery = "SELECT COUNT(a.id) FROM Article a WHERE a.type = :type AND a.status = :status AND a.title LIKE %:keyword%")
    Page<Article> searchByStatus(@Param("type") ArticleType type, @Param("keyword") String searchKeyword,
                                 Pageable pageable, @Param("status") ArticleStatusEnum status);

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.tags LEFT JOIN FETCH a.category " +
            "WHERE a.type = :type AND a.status = :status AND a.author.username = :username",
            countQuery = "SELECT COUNT(a.id) FROM Article a WHERE a.type = :type AND a.status = :status " +
                    "AND a.author.username = :username")
    Page<Article> findByStatusAndAuthor(@Param("type") ArticleType type, @Param("username") String username,
                                        @Param("status") ArticleStatusEnum status, Pageable pageable);

    @Query(value = "SELECT a FROM Article a INNER JOIN FETCH a.author LEFT JOIN FETCH a.tags LEFT JOIN FETCH a.category " +
            "WHERE a.type = :type AND a.status = :status AND a.author.username = :username AND a.title LIKE %:keyword%",
            countQuery = "SELECT COUNT(a.id) FROM Article a WHERE a.type = :type AND a.status = :status " +
                    "AND a.author.username = :username AND a.title LIKE %:keyword%")
    Page<Article> searchByStatusAndAuthor(@Param("type") ArticleType type, @Param("username") String username,
                                          @Param("keyword") String keyword,
                                          @Param("status") ArticleStatusEnum status, Pageable pageable);

    @Query("SELECT COUNT(a.id) FROM Article a WHERE a.id in (?1) " +
            "AND a.type = com.debugbybrain.blog.entity.enums.ArticleType.POST " +
            "AND a.status = com.debugbybrain.blog.entity.enums.ArticleStatusEnum.PUBLISHED_GLOBAL " +
            "AND a.author.username = ?2 ")
    long countValidYourPublishedArticle(Set<Long> articleIds, String authorUsername);
}
