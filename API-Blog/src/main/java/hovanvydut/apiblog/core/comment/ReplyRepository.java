package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.core.comment.dto.ReplyDTO;
import hovanvydut.apiblog.model.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/25/21
 */

@Repository
public interface ReplyRepository extends PagingAndSortingRepository<Reply, Long> {

    @Query("SELECT c.id FROM Reply r INNER JOIN Comment c ON r.comment.id = c.id WHERE r.id = :replyId")
    Optional<Long> getCommentIdIdById(@Param("replyId") long replyId);

    @Query("SELECT new hovanvydut.apiblog.core.comment.dto.ReplyDTO(r.id, r.content, r.imageSlug, r.createdAt, r.updatedAt, u.fullName, u.username) FROM Reply r INNER JOIN User u ON u.id = r.fromUser.id WHERE r.comment.id = :commentId")
    Page<ReplyDTO> getAllReplyOfComment(@Param("commentId") long commentId, Pageable pageable);
}
