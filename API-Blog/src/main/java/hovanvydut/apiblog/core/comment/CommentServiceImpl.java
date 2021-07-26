package hovanvydut.apiblog.core.comment;

import hovanvydut.apiblog.common.enums.ReplyTypeEnum;
import hovanvydut.apiblog.common.exception.CommentNotFoundException;
import hovanvydut.apiblog.common.exception.MyUsernameNotFoundException;
import hovanvydut.apiblog.common.exception.ReplyNotFoundException;
import hovanvydut.apiblog.core.article.ArticleRepository;
import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateCommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateReplytDTO;
import hovanvydut.apiblog.core.comment.dto.ReplyDTO;
import hovanvydut.apiblog.core.user.UserRepository;
import hovanvydut.apiblog.model.entity.Article;
import hovanvydut.apiblog.model.entity.Comment;
import hovanvydut.apiblog.model.entity.Reply;
import hovanvydut.apiblog.model.entity.User;
import org.modelmapper.ModelMapper;
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

    private final CommentRepository commentRepo;
    private final ModelMapper modelMapper;
    private final UserRepository userRepo;
    private final ArticleRepository articleRepo;
    private final ReplyRepository replyRepo;

    public CommentServiceImpl(CommentRepository commentRepo,
                              ModelMapper modelMapper,
                              UserRepository userRepository,
                              ArticleRepository articleRepository,
                              ReplyRepository replyRepo) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepository;
        this.articleRepo = articleRepository;
        this.replyRepo = replyRepo;
    }

    @Override
    public Page<CommentDTO> getAllCommentOfArticle(String articleSlug, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Long articleId = this.articleRepo.getArticleIdBySlug(articleSlug)
                .orElseThrow(() -> new RuntimeException("Article ID not found"));

        return this.commentRepo.findByArticleId(articleId, pageable);
    }

    @Override
    public Page<ReplyDTO> getAllRepliesOfComment(long commentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReplyDTO> pageReplyDTO = this.replyRepo.getAllReplyOfComment(commentId, pageable);
        return pageReplyDTO;
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

    @Override
    @Transactional
    public ReplyDTO  replyOfComment(long commentId, CreateReplytDTO createReplytDTO, String commentorUsername) {
        this.commentRepo.getCommentIdById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        Long commentorId = this.userRepo.getUserIdByUsername(commentorUsername)
                .orElseThrow(() -> new MyUsernameNotFoundException(commentorUsername));

        Reply reply = this.modelMapper.map(createReplytDTO, Reply.class);
        reply.setComment(new Comment().setId(commentId));
        reply.setReplyType(ReplyTypeEnum.COMMENT);
        reply.setFromUser(new User().setId(commentId));

        Reply savedReply = this.replyRepo.save(reply);

        return this.modelMapper.map(savedReply, ReplyDTO.class);
    }

    @Override
    @Transactional
    public ReplyDTO replyOfReply(long replyId, CreateReplytDTO createReplytDTO, String commentorUsername) {
        Long commentId = this.replyRepo.getCommentIdIdById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));
        Long commentorId = this.userRepo.getUserIdByUsername(commentorUsername).orElseThrow(() -> new MyUsernameNotFoundException(commentorUsername));

        Reply reply = this.modelMapper.map(createReplytDTO, Reply.class);
        reply.setComment(new Comment().setId(commentId));
        reply.setParentReply(new Reply().setId(replyId));
        reply.setReplyType(ReplyTypeEnum.REPLY);
        reply.setFromUser(new User().setId(commentorId));

        Reply savedReply = this.replyRepo.save(reply);

        return this.modelMapper.map(savedReply, ReplyDTO.class);
    }

}
