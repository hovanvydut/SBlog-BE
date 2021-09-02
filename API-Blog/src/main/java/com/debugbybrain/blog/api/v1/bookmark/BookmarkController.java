package com.debugbybrain.blog.api.v1.bookmark;

import com.debugbybrain.blog.api.v1.article.dto.ArticlePageResp;
import com.debugbybrain.blog.api.v1.bookmark.dto.BookmarkPaginationParams;
import com.debugbybrain.blog.core.article.dto.ArticleDTO;
import com.debugbybrain.blog.core.bookmark.BookmarkService;
import com.debugbybrain.blog.core.bookmark.dto.SubscriberDTO;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@RestController
@RequestMapping("/api/v1")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final ModelMapper modelMapper;

    public BookmarkController(BookmarkService bookmarkService, ModelMapper modelMapper) {
        this.bookmarkService = bookmarkService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get all clipped articles")
    @GetMapping("/users/{username}/clipped-articles")
    public ResponseEntity<ArticlePageResp> getClippedArticlesOfUser(@PathVariable String username,
                                                                    @Valid BookmarkPaginationParams params) {
        Page<ArticleDTO> pageArticleDTO = this.bookmarkService
                .getAllClippedArticlesOfUser(username, params.getPage(), params.getSize(), params.getSort(), params.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(pageArticleDTO, ArticlePageResp.class));
    }

    @ApiOperation(value = "Get all subscriber of the article")
    @GetMapping("/articles/{slug}/subscribers")
    public ResponseEntity<List<SubscriberDTO>> getAllSubscribersOfArticle(@PathVariable String slug) {
        List<SubscriberDTO> subscriberDTOs = this.bookmarkService.getAllSubscribers(slug);

        return ResponseEntity.ok(subscriberDTOs);
    }

    @ApiOperation(value = "Clip a article")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/subscriptions/articles/{article-slug}")
    public void clipArticle(@PathVariable("article-slug") String articleSlug,
                             Principal principal) {
        String subscriberUsername = principal.getName();
        this.bookmarkService.clipArticle(articleSlug, subscriberUsername);
    }

    @ApiOperation(value = "delete a clipped article")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/me/subscriptions/articles/{article-slug}")
    public void deleteClipArticles(@PathVariable("article-slug") String articleSlug,
                             Principal principal) {
        String subscriberUsername = principal.getName();
        this.bookmarkService.deleteBookmark(articleSlug, subscriberUsername);
    }

}
