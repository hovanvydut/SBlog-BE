package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.article.id = :articleId")
    Page<Comment> findByArticleId(@Param("articleId") Long articleId, Pageable pageable);

}
