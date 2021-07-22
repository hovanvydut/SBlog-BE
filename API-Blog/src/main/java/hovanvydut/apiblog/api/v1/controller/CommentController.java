package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.request.BookmarkPaginationParams;
import hovanvydut.apiblog.api.v1.request.CommentArticlePaginationParams;
import hovanvydut.apiblog.api.v1.request.CreateCommentReq;
import hovanvydut.apiblog.api.v1.request.CreateReplyReq;
import hovanvydut.apiblog.api.v1.response.CommentArticlePageResp;
import hovanvydut.apiblog.core.comment.CommentService;
import hovanvydut.apiblog.core.comment.dto.CommentDTO;
import hovanvydut.apiblog.core.comment.dto.CreateCommentDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
    public ResponseEntity<CommentArticlePageResp> getAllCommentOfArticle(@PathVariable String articleSlug, @Valid CommentArticlePaginationParams req) {
        Page<CommentDTO> commentDTOPage = this.commentService
                .getAllCommentOfArticle(articleSlug, req.getPage(), req.getSize());

        return ResponseEntity.ok(this.modelMapper.map(commentDTOPage, CommentArticlePageResp.class));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/{articleSlug}/comments")
    public void comment(@PathVariable String articleSlug, @Valid @RequestBody CreateCommentReq req, Principal principal) {
        CreateCommentDTO commentDTO = this.modelMapper.map(req, CreateCommentDTO.class);
        this.commentService.commentArticle(articleSlug, commentDTO, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comments/{commentId}/reply")
    public void replyComment() {

    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId, Principal principal) {
        String ownerUsername = principal.getName();
        this.commentService.deleteComment(commentId, ownerUsername);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/{articleSlug}/comments/{commentId}/reply")
    public void replyComment(@PathVariable String articleSlug, @PathVariable long commentId, @Valid CreateReplyReq req, Principal principal) {

    }

}
