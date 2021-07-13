package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.request.CreateArticleReq;
import hovanvydut.apiblog.api.v1.request.UpdateArticleReq;
import hovanvydut.apiblog.common.constant.ArticleStatusEnum;
import hovanvydut.apiblog.core.article.ArticleService;
import hovanvydut.apiblog.core.article.dto.ArticleDTO;
import hovanvydut.apiblog.core.article.dto.CreateArticleDTO;
import hovanvydut.apiblog.core.article.dto.PublishOption;
import hovanvydut.apiblog.core.article.dto.UpdateArticleDTO;
import org.modelmapper.ModelMapper;
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

    /**
     * Anyone can get Article with status is PUBLISHED_GLOBAL or PUBLISHED_LINK. User logged in can get owning Article
     * with any status
     * @param slug
     * @param principal
     */
    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleDTO> getPublishedArticle(@PathVariable String slug, Principal principal) {
        // only get article with status published | only_who_has_link (not required login), or user logged in can get owing article with any status
        // get with author info (following, followers, count articles)
        String usernameViewer = null;
        if (principal != null) {
            usernameViewer = principal.getName();
        }

        ArticleDTO articleDTO = this.articleService.getArticle(slug, usernameViewer);

        return ResponseEntity.ok(articleDTO);
    }

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

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PatchMapping("/articles/{slug}/approve-publish")
    public void approvePublishArticle(@PathVariable String slug) {
        this.articleService.approveArticle(slug);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PatchMapping("/articles/{slug}/spam")
    public void markArticleSpam(@PathVariable String slug) {
        this.articleService.markArticleSpam(slug);
    }

}
