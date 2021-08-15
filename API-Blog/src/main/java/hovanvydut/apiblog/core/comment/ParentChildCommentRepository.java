package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.entity.ParentChildComment;
import hovanvydut.apiblog.entity.ParentChildCommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 8/7/21
 */

@Repository
public interface ParentChildCommentRepository extends JpaRepository<ParentChildComment, ParentChildCommentId> {

    @Modifying
    @Query(value = "INSERT INTO parent_child_comment (parent_comment_id, child_comment_id, level) " +
            "((SELECT pc.parent_comment_id, :childCommentId, pc.level + 1 FROM parent_child_comment pc " +
            "WHERE pc.child_comment_id = :parentCommentId) UNION (SELECT :childCommentId, :childCommentId, 0))",
            nativeQuery = true)
    int insertNewChildComment(@Param("childCommentId") long childCommentId, @Param("parentCommentId") long parentCommentId);

    @Modifying
    @Query(value = "INSERT INTO parent_child_comment (parent_comment_id, child_comment_id, level) VALUES (?1, ?1, 0)", nativeQuery = true)
    int insertNewRootComment(long commentId);

}
