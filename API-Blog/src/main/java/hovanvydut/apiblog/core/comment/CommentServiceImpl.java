package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.common.exception.CommentNotFoundException;
import hovanvydut.apiblog.core.article.ArticleService;
import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateCommentDTO;
import hovanvydut.apiblog.core.user.UserService;
import hovanvydut.apiblog.entity.Article;
import hovanvydut.apiblog.entity.Comment;
import hovanvydut.apiblog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@Validated
@Service
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentRepository commentRepo;
    private final ParentChildCommentRepository parentChildCommentRepo;

    public CommentServiceImpl(UserService userService, ArticleService articleService, CommentRepository commentRepo,
                              ParentChildCommentRepository parentChildCommentRepo) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentRepo = commentRepo;
        this.parentChildCommentRepo = parentChildCommentRepo;
    }

    @Override
    public Page<CommentDTO> getAllCommentOfArticle(String articleSlug, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Long articleId = this.articleService.getArticleIdBySlug(articleSlug);

        return this.commentRepo.getRootCommentByArticleId(articleId, pageable);
    }

    @Override
    public Page<CommentDTO> getAllRepliesOfComment(long commentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return this.commentRepo.getAllChildOfComment(commentId, pageable);
    }


    @Override
    @Transactional
    public void commentArticle(String articleSlug, CreateCommentDTO commentDTO, String fromUsername) {
        Long fromUserId = this.userService.getUserIdByUsername(fromUsername);
        Long articleId = this.articleService.getArticleIdBySlug(articleSlug);

        Comment comment = new Comment()
                .setContent(commentDTO.getContent())
                .setImageSlug(commentDTO.getImageSlug())
                .setArticle(new Article().setId(articleId))
                .setFromUser(new User().setId(fromUserId));

        Long ancestorId = commentDTO.getAncestorId();
        if (ancestorId == null) {
            comment.setRoot(true);
        } else {
            this.commentRepo.getCommentIdById(ancestorId)
                    .orElseThrow(() -> new CommentNotFoundException(ancestorId));
            comment.setRoot(false);
        }

        Comment savedComment = this.commentRepo.save(comment);

        if (commentDTO.getAncestorId() != null) {
            this.parentChildCommentRepo.insertNewChildComment(savedComment.getId(), commentDTO.getAncestorId());
        } else {
            this.parentChildCommentRepo.insertNewRootComment(savedComment.getId());
        }
    }

    @Override
    @Transactional
    public void deleteComment(long commentId, String ownerUsername) {
        String username = this.commentRepo.getOwnerUsernameOfComment(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (ownerUsername.equals(username)) {
            this.commentRepo.permanentlyDelete(commentId);
        } else {
            // TODO: add custom exception
            throw new RuntimeException("You're not owner this comment");
        }
    }

}
