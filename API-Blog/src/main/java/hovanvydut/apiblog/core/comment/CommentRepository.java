package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.entity.Comment;
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
            "c.updatedAt, c.fromUser.fullName, c.fromUser.username, c.fromUser.avatar, count(pc.childComment.id) - 1) FROM Comment c " +
            "LEFT JOIN ParentChildComment pc ON pc.parentComment.id = c.id " +
            "WHERE c.article.id = :articleId AND c.root = true GROUP BY c.id")
    Page<CommentDTO> getRootCommentByArticleId(@Param("articleId") Long articleId, Pageable pageable);

    @Query("SELECT new hovanvydut.apiblog.core.comment.dto.CommentDTO(c.id, c.content, c.imageSlug, c.createdAt, " +
            "c.updatedAt, c.fromUser.fullName, c.fromUser.username, c.fromUser.avatar) FROM ParentChildComment pc " +
            "INNER JOIN Comment c ON pc.childComment.id = c.id WHERE pc.parentComment.id = :commentId " +
            "AND pc.childComment.id <> :commentId")
    Page<CommentDTO> getAllChildOfComment(@Param("commentId") long commentId, Pageable pageable);

    @Query(value = "SELECT count(c.id) FROM comment c WHERE c.article_id = ?1", nativeQuery = true)
    long countByArticleId(Long articleId);

    @Modifying
    @Query(value = "DELETE FROM comment c WHERE c.id IN " +
            "(SELECT DISTINCT child_comment_id FROM parent_child_comment pc WHERE pc.parent_comment_id = ?1)", nativeQuery = true)
    int permanentlyDelete(long commentId);

    @Query("SELECT c.fromUser.username FROM Comment c WHERE c.id = ?1")
    Optional<String> getOwnerUsernameOfComment(long commentId);

}
