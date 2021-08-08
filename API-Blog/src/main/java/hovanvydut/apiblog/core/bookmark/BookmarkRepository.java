package hovanvydut.apiblog.core.bookmark;

import hovanvydut.apiblog.core.bookmark.dto.SubscriberDTO;
import hovanvydut.apiblog.entity.Article;
import hovanvydut.apiblog.entity.BookmarkArticle;
import hovanvydut.apiblog.entity.BookmarkArticleId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkArticle, BookmarkArticleId> {

    @Query(value = "SELECT u.id as id, u.fullName as fullName, u.username as username FROM BookmarkArticle b INNER JOIN User u ON b.id.userId = u.id WHERE b.id.articleId = :articleId")
    List<SubscriberDTO> findAllSubscribersOfArticle(@Param("articleId") long articleId);

    @Query(value = "SELECT a FROM BookmarkArticle b INNER JOIN Article a ON b.id.articleId = a.id WHERE b.id.userId = :userId")
    Page<Article> findAllClippedArticlesOfUser(@Param("userId") long userId, Pageable pageable);

    @Query(value = "SELECT a FROM BookmarkArticle b INNER JOIN Article a ON b.id.articleId = a.id WHERE b.id.userId = :userId AND a.title LIKE %:keyword%")
    Page<Article> searchByArticleTitle(@Param("keyword") String keyword, @Param("userId") long userId, Pageable pageable);

}

