package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.request.CommentArticlePaginationParams;
import hovanvydut.apiblog.api.v1.request.CreateCommentReq;
import hovanvydut.apiblog.api.v1.request.CreateReplyCommentReq;
import hovanvydut.apiblog.api.v1.response.CommentArticlePageResp;
import hovanvydut.apiblog.api.v1.response.ReplyResp;
import hovanvydut.apiblog.core.comment.CommentService;
import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateCommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateReplytDTO;
import hovanvydut.apiblog.core.comment.dto.ReplyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/articles/{articleSlug}/comments")
    public ResponseEntity<CommentArticlePageResp> getAllCommentOfArticle(@PathVariable String articleSlug,
                                                                         @Valid CommentArticlePaginationParams req) {
        Page<CommentDTO> commentDTOPage = this.commentService
                .getAllCommentOfArticle(articleSlug, req.getPage(), req.getSize());

        return ResponseEntity.ok(this.modelMapper.map(commentDTOPage, CommentArticlePageResp.class));
    }

    @GetMapping("/articles/comments/{commentId}/replies")
    public ResponseEntity<CommentArticlePageResp> getAllRepliesOfComment(@PathVariable long commentId, @Valid CommentArticlePaginationParams req) {
        Page<ReplyDTO> replyDTOPage = this.commentService
                .getAllRepliesOfComment(commentId, req.getPage(), req.getSize());

        return ResponseEntity.ok(this.modelMapper.map(replyDTOPage, CommentArticlePageResp.class));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/{articleSlug}/comments")
    public void comment(@PathVariable String articleSlug, @Valid @RequestBody CreateCommentReq req, Principal principal) {
        CreateCommentDTO commentDTO = this.modelMapper.map(req, CreateCommentDTO.class);
        this.commentService.commentArticle(articleSlug, commentDTO, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/comments/{commentId}/replies")
    public ResponseEntity<ReplyResp> replyComment(@PathVariable long commentId,
                                                  @Valid @RequestBody CreateReplyCommentReq req,
                                                  Principal principal) {

        CreateReplytDTO replytDTO = this.modelMapper.map(req, CreateReplytDTO.class);
        String commentorUsername = principal.getName();

        ReplyDTO replyDTO = this.commentService.replyOfComment(commentId, replytDTO, commentorUsername);

        return ResponseEntity.ok(this.modelMapper.map(replyDTO, ReplyResp.class));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/comments/replies/{replyId}")
    public ResponseEntity<ReplyResp> replyOfReply(@PathVariable long replyId,
                                                  @Valid @RequestBody CreateReplyCommentReq req,
                                                  Principal principal) {
        CreateReplytDTO replytDTO = this.modelMapper.map(req, CreateReplytDTO.class);
        String commentorUsername = principal.getName();

        ReplyDTO replyDTO = this.commentService.replyOfReply(replyId, replytDTO, commentorUsername);

        return ResponseEntity.ok(this.modelMapper.map(replyDTO, ReplyResp.class));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/articles/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId, Principal principal) {
        String ownerUsername = principal.getName();
        this.commentService.deleteComment(commentId, ownerUsername);
    }
}
