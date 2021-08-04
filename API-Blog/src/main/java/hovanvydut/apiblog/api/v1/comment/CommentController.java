package hovanvydut.apiblog.api.v1.comment;

import hovanvydut.apiblog.api.v1.comment.dto.*;
import hovanvydut.apiblog.core.comment.CommentService;
import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateCommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateReplytDTO;
import hovanvydut.apiblog.core.comment.dto.ReplyDTO;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Get all comments")
    @GetMapping("/articles/{articleSlug}/comments")
    public ResponseEntity<CommentArticlePageResp> getAllCommentOfArticle(@PathVariable String articleSlug,
                                                                         @Valid CommentArticlePaginationParams params) {
        Page<CommentDTO> commentDTOPage = this.commentService
                .getAllCommentOfArticle(articleSlug, params.getPage(), params.getSize());

        return ResponseEntity.ok(this.modelMapper.map(commentDTOPage, CommentArticlePageResp.class));
    }

    @ApiOperation(value = "Get all replies of a comment")
    @GetMapping("/articles/comments/{commentId}/replies")
    public ResponseEntity<CommentArticlePageResp> getAllRepliesOfComment(@PathVariable long commentId,
                                                                         @Valid CommentArticlePaginationParams params) {
        Page<ReplyDTO> replyDTOPage = this.commentService
                .getAllRepliesOfComment(commentId, params.getPage(), params.getSize());

        return ResponseEntity.ok(this.modelMapper.map(replyDTOPage, CommentArticlePageResp.class));
    }

    @ApiOperation(value = "comment the article")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/{articleSlug}/comments")
    public void comment(@PathVariable String articleSlug, @Valid @RequestBody CreateCommentReq req, Principal principal) {
        CreateCommentDTO commentDTO = this.modelMapper.map(req, CreateCommentDTO.class);
        this.commentService.commentArticle(articleSlug, commentDTO, principal.getName());
    }

    @ApiOperation(value = "reply a comment")
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

    @ApiOperation(value = "Reply of reply")
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

    @ApiOperation(value = "delete comment")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/articles/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId, Principal principal) {
        String ownerUsername = principal.getName();
        this.commentService.deleteComment(commentId, ownerUsername);
    }

    @ApiOperation(value = "Delete a reply")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/articles/comments/replies/{replyId}")
    public void deleteReply(@PathVariable long replyId, Principal principal) {
        this.commentService.deleteReply(replyId, principal.getName());
    }

}
