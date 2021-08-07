package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

    @Query("SELECT c.id FROM Comment c WHERE c.id = :commentId")
    Optional<Long> getCommentIdById(@Param("commentId") long commentId);

    @Query("SELECT new hovanvydut.apiblog.core.comment.dto.CommentDTO(c.id, c.content, c.imageSlug, c.createdAt, " +
            "c.updatedAt, c.fromUser.fullName, c.fromUser.username, c.fromUser.avatar, count(r.id)) FROM Comment c " +
            "LEFT JOIN Reply r ON r.comment.id = c.id WHERE c.article.id = :articleId GROUP BY c.id")
    Page<CommentDTO> findByArticleId(@Param("articleId") Long articleId, Pageable pageable);

    @Modifying
    @Query("UPDATE Comment c SET c.deleted = true WHERE c.id = ?1")
    int softDelete(long commentId);
}
