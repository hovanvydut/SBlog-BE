package hovanvydut.apiblog.api.v1.bookmark;

import hovanvydut.apiblog.api.v1.article.dto.ArticlePageResp;
import hovanvydut.apiblog.api.v1.bookmark.dto.BookmarkPaginationParams;
import hovanvydut.apiblog.core.article.dto.ArticleDTO;
import hovanvydut.apiblog.core.bookmark.BookmarkService;
import hovanvydut.apiblog.core.bookmark.dto.SubscriberDTO;
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

    /**
     * ?sort=published_at|clipped_at&page=X&size=X (pagination)
     * User can see marked articles (only published_global and published_link article)
     */
    @GetMapping("/users/{username}/clipped-articles")
    public ResponseEntity<ArticlePageResp> getClippedArticlesOfUser(@PathVariable String username,
                                                                    @Valid BookmarkPaginationParams req) {
        Page<ArticleDTO> pageArticleDTO = this.bookmarkService
                .getAllClippedArticlesOfUser(username, req.getPage(), req.getSize(), req.getSort(), req.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(pageArticleDTO, ArticlePageResp.class));
    }

    @GetMapping("/articles/{slug}/subscribers")
    public ResponseEntity<List<SubscriberDTO>> getAllSubscribersOfArticle(@PathVariable String slug) {
        List<SubscriberDTO> subscriberDTOs = this.bookmarkService.getAllSubscribers(slug);

        return ResponseEntity.ok(subscriberDTOs);
    }

    /**
     *  Author can't clipped owning articles
     * @param articleSlug
     * @param principal
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/subscriptions/articles/{article-slug}")
    public void clipArticle(@PathVariable("article-slug") String articleSlug,
                             Principal principal) {
        String subscriberUsername = principal.getName();

        this.bookmarkService.clipArticle(articleSlug, subscriberUsername);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/me/subscriptions/articles/{article-slug}")
    public void deleteClipArticles(@PathVariable("article-slug") String articleSlug,
                             Principal principal) {
        String subscriberUsername = principal.getName();

        this.bookmarkService.deleteBookmark(articleSlug, subscriberUsername);
    }

}
