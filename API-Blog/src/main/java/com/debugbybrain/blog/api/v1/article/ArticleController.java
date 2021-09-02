package com.debugbybrain.blog.api.v1.article;

import com.debugbybrain.blog.api.v1.article.dto.ArticlePageResp;
import com.debugbybrain.blog.api.v1.article.dto.ArticlePaginationParams;
import com.debugbybrain.blog.api.v1.article.dto.CreateArticleReq;
import com.debugbybrain.blog.api.v1.article.dto.UpdateArticleReq;
import com.debugbybrain.blog.api.v1.user.dto.UserPaginationParams;
import com.debugbybrain.blog.core.article.ArticleService;
import com.debugbybrain.blog.core.article.dto.ArticleDTO;
import com.debugbybrain.blog.core.article.dto.CreateArticleDTO;
import com.debugbybrain.blog.core.article.dto.PublishOption;
import com.debugbybrain.blog.core.article.dto.UpdateArticleDTO;
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
 * Created on 7/12/21
 */

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get all articles")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @GetMapping("/articles")
    public ResponseEntity<ArticlePageResp> getAllArticles(@Valid ArticlePaginationParams params) {
        Page<ArticleDTO> page = this.articleService.getAllArticles(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(page, ArticlePageResp.class));
    }

    @ApiOperation(value = "Get all published articles")
    @GetMapping("/articles/published")
    public ResponseEntity<ArticlePageResp> getAllPublishedArticles(@Valid ArticlePaginationParams params) {
        Page<ArticleDTO> page = this.articleService.getAllPublishedArticles(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(page, ArticlePageResp.class));
    }

    @ApiOperation(value = "Get all published articled of user")
    @GetMapping("/users/{username}/articles/published")
    public Page<ArticleDTO> getAllPublishedArticlesOfUser(@PathVariable String username, @Valid UserPaginationParams params) {
        // get all published article of user
        Page<ArticleDTO> articleDTOList = this.articleService
                .getAllPublishedArticles(username, params.getPage(), params.getSize(), params.getSort(), params.getKeyword());
        return articleDTOList;
    }


    @ApiOperation(value = "Get article by slug")
    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleDTO> getPublishedArticle(@PathVariable String slug, Principal principal) {
        String usernameViewer = null;
        if (principal != null) {
            usernameViewer = principal.getName();
        }

        ArticleDTO articleDTO = this.articleService.getArticle(slug, usernameViewer);

        return ResponseEntity.ok(articleDTO);
    }

    @ApiOperation(value = "Create new article")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles")
    public ResponseEntity<ArticleDTO> createNewArticle(@Valid @RequestBody CreateArticleReq req,
                                                       Principal principal,
                                                       @RequestParam("publish") PublishOption publishOption) {

        CreateArticleDTO dto = this.modelMapper.map(req, CreateArticleDTO.class);
        String authorUsername = principal.getName();

        ArticleDTO articleDTO = this.articleService.createNewArticle(dto, publishOption, authorUsername);

        return ResponseEntity.ok(articleDTO);
    }

    @ApiOperation(value = "Update article by slug")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/articles/{slug}")
    public ResponseEntity<ArticleDTO> updateArticle(@Valid @RequestBody UpdateArticleReq req,
                                                    Principal principal,
                                                    @RequestParam("publish") PublishOption publishOption,
                                                    @PathVariable String slug) {
        UpdateArticleDTO dto = this.modelMapper.map(req, UpdateArticleDTO.class);
        String authorUsername = principal.getName();

        ArticleDTO articleDTO = this.articleService.updateArticle(slug, dto, publishOption, authorUsername);

        return ResponseEntity.ok(articleDTO);
    }

    @ApiOperation(value = "Approve a pending article")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PatchMapping("/articles/{slug}/approve-publish")
    public void approvePublishArticle(@PathVariable String slug) {
        this.articleService.approveArticle(slug);
    }

    @ApiOperation(value = "Mark a articles as spam")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PatchMapping("/articles/{slug}/spam")
    public void markArticleSpam(@PathVariable String slug) {
        this.articleService.markArticleSpam(slug);
    }

    @ApiOperation(value = "Delete article")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/articles/{slug}")
    public void deleteArticle(@PathVariable String slug, Principal principal) {
        this.articleService.deleteArticle(slug, principal.getName());
    }

}
