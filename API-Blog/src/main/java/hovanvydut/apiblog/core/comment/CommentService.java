package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateCommentDTO;
import org.springframework.data.domain.Page;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public interface CommentService {
    Page<CommentDTO> getAllCommentOfArticle(String articleSlug, int page, int size);
    public void commentArticle(String articleSlug, CreateCommentDTO commentDTO, String fromUsername);
    public void deleteComment(long commentId, String ownerUsername);
}
