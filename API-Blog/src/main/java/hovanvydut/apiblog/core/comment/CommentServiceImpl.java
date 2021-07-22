package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.common.exception.CommentNotFoundException;
import hovanvydut.apiblog.common.exception.MyUsernameNotFoundException;
import hovanvydut.apiblog.core.article.ArticleRepository;
import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateCommentDTO;
import hovanvydut.apiblog.core.user.UserRepository;
import hovanvydut.apiblog.model.entity.Article;
import hovanvydut.apiblog.model.entity.Comment;
import hovanvydut.apiblog.model.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@Validated
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final ModelMapper modelMapper;
    private final UserRepository userRepo;
    private final ArticleRepository articleRepo;

    public CommentServiceImpl(CommentRepository commentRepo,
                              ModelMapper modelMapper,
                              UserRepository userRepository,
                              ArticleRepository articleRepository) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepository;
        this.articleRepo = articleRepository;
    }

    @Override
    public Page<CommentDTO> getAllCommentOfArticle(String articleSlug, int page, int size) {
        Pageable pageable = PageRequest.of(0, 10);

        Long articleId = this.articleRepo.getArticleIdBySlug(articleSlug)
                .orElseThrow(() -> new RuntimeException("Article ID not found"));

        return this.commentRepo.findByArticleId(articleId, pageable);
//        Page<Comment> commentPage = this.commentRepo.findByArticleId(articleId, pageable);
//
//        List<Comment> comments = commentPage.getContent();
//        List<CommentDTO> commentDTOList = this.modelMapper.map(comments, new TypeToken<List<Comment>>() {}.getType());
//
//        return new PageImpl<>(commentDTOList, pageable, commentPage.getTotalElements());
    }

    @Override
    @Transactional
    public void commentArticle(String articleSlug, CreateCommentDTO commentDTO, String fromUsername) {
        Long fromUserId = this.userRepo.getUserIdByUsername(fromUsername)
                .orElseThrow(() -> new MyUsernameNotFoundException(fromUsername));

        Long articleId = this.articleRepo.getPublishedArticleIdBySlug(articleSlug).orElseThrow(
                () -> new RuntimeException("Article ID not found")
        );

        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setArticle(new Article().setId(articleId))
                .setFromUser(new User().setId(fromUserId));

        this.commentRepo.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(long commentId, String ownerUsername) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (comment.getFromUser().getUsername().equals(ownerUsername)) {
            this.commentRepo.delete(comment);
        } else {
            throw new RuntimeException("You're not owner this comment");
        }
    }
}
