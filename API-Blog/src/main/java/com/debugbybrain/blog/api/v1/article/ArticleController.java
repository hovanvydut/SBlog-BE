package com.debugbybrain.blog.api.v1.article;

import com.debugbybrain.blog.api.v1.article.dto.*;
import com.debugbybrain.blog.api.v1.user.dto.UserPaginationParams;
import com.debugbybrain.blog.core.article.ArticleService;
import com.debugbybrain.blog.core.article.dto.*;
import com.debugbybrain.blog.entity.Article;
import com.debugbybrain.blog.entity.enums.ArticleType;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

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
        Page<ArticleDTO> page = this.articleService.getAllArticlesOrSeries(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword(), ArticleType.POST);

        return ResponseEntity.ok(this.modelMapper.map(page, ArticlePageResp.class));
    }

    @ApiOperation(value = "Get all series")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @GetMapping("/series")
    public ResponseEntity<ArticlePageResp> getAllSeries(@Valid ArticlePaginationParams params) {
        Page<ArticleDTO> page = this.articleService.getAllArticlesOrSeries(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword(), ArticleType.SERIES);

        return ResponseEntity.ok(this.modelMapper.map(page, ArticlePageResp.class));
    }

    @ApiOperation(value = "Get all published articles")
    @GetMapping("/articles/published")
    public ResponseEntity<ArticlePageResp> getAllPublishedArticles(@Valid ArticlePaginationParams params) {
        Page<ArticleDTO> page = this.articleService.getAllPublishedArticlesOrSeries(Optional.empty(), ArticleType.POST,
                params.getPage(), params.getSize(), params.getSort(), params.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(page, ArticlePageResp.class));
    }

    @ApiOperation(value = "Get all published series")
    @GetMapping("/series/published")
    public ResponseEntity<ArticlePageResp> getAllPublishedSeries(@Valid ArticlePaginationParams params) {
        Page<ArticleDTO> page = this.articleService.getAllPublishedArticlesOrSeries(Optional.empty(), ArticleType.SERIES,
                params.getPage(), params.getSize(), params.getSort(), params.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(page, ArticlePageResp.class));
    }

    @ApiOperation(value = "Get all published articled of user")
    @GetMapping("/users/{username}/articles/published")
    public Page<ArticleDTO> getAllPublishedArticlesOfUser(@PathVariable String username, @Valid UserPaginationParams params) {
        Page<ArticleDTO> articleDTOList = this.articleService
                .getAllPublishedArticlesOrSeries(Optional.ofNullable(username), ArticleType.POST, params.getPage(),
                        params.getSize(), params.getSort(), params.getKeyword());
        return articleDTOList;
    }

    @ApiOperation(value = "Get all published series of user")
    @GetMapping("/users/{username}/series/published")
    public Page<ArticleDTO> getAllPublishedSeriesOfUser(@PathVariable String username, @Valid UserPaginationParams params) {
        Page<ArticleDTO> articleDTOList = this.articleService
                .getAllPublishedArticlesOrSeries(Optional.ofNullable(username), ArticleType.SERIES, params.getPage(),
                        params.getSize(), params.getSort(), params.getKeyword());
        return articleDTOList;
    }

    @ApiOperation(value = "Get the article by slug")
    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleDTO> getPublishedArticle(@PathVariable String slug, Principal principal) {
        String usernameViewer = null;
        if (principal != null) {
            usernameViewer = principal.getName();
        }

        ArticleDTO articleDTO = this.articleService.getArticle(slug, usernameViewer);

        return ResponseEntity.ok(articleDTO);
    }

    @ApiOperation(value = "Get the series by slug")
    @GetMapping("/series/{slug}")
    public ResponseEntity<SeriesDTO> getPublishedSeries(@PathVariable String slug, Principal principal) {
        String usernameViewer = null;
        if (principal != null) {
            usernameViewer = principal.getName();
        }

        SeriesDTO seriesDTO = this.articleService.getSeries(slug, usernameViewer);

        return ResponseEntity.ok(seriesDTO);
    }

    @ApiOperation(value = "Create new article")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles")
    public ResponseEntity<ArticleDTO> createNewArticle(@Valid @RequestBody CreateArticleReq req,
                                                       Principal principal,
                                                       @RequestParam("publish") PublishOption publishOption) {

        CreateArticleDTO dto = this.modelMapper.map(req, CreateArticleDTO.class);
        String authorUsername = principal.getName();

        ArticleDTO articleDTO = this.articleService.createNewArticle(dto, publishOption, authorUsername, false);

        return ResponseEntity.ok(articleDTO);
    }

    @ApiOperation(value = "Create a new series")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/series")
    public void createSeries(@Valid @RequestBody CreateSeriesReq req, Principal principal,
                             @RequestParam("publish") PublishOption publishOption) {

        CreateArticleDTO dto = this.modelMapper.map(req, CreateArticleDTO.class);
        String authorUsername = principal.getName();

        ArticleDTO articleDTO = this.articleService.createNewArticle(dto, publishOption, authorUsername, true);

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
