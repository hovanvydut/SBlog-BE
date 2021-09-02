package com.debugbybrain.blog.api.v1.comment;

import com.debugbybrain.blog.api.v1.comment.dto.CommentArticlePageResp;
import com.debugbybrain.blog.api.v1.comment.dto.CommentArticlePaginationParams;
import com.debugbybrain.blog.api.v1.comment.dto.CreateCommentReq;
import com.debugbybrain.blog.core.comment.CommentService;
import com.debugbybrain.blog.core.comment.dto.CommentDTO;
import com.debugbybrain.blog.core.comment.dto.CreateCommentDTO;
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
        Page<CommentDTO> commentDTOPage = this.commentService
                .getAllRepliesOfComment(commentId, params.getPage(), params.getSize());

        return ResponseEntity.ok(this.modelMapper.map(commentDTOPage, CommentArticlePageResp.class));
    }

    @ApiOperation(value = "comment the article")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/{articleSlug}/comments")
    public void comment(@PathVariable String articleSlug, @Valid @RequestBody CreateCommentReq req, Principal principal) {
        CreateCommentDTO commentDTO = this.modelMapper.map(req, CreateCommentDTO.class);
        this.commentService.commentArticle(articleSlug, commentDTO, principal.getName());
    }


    @ApiOperation(value = "delete comment")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/articles/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId, Principal principal) {
        String ownerUsername = principal.getName();
        this.commentService.deleteComment(commentId, ownerUsername);
    }

}
