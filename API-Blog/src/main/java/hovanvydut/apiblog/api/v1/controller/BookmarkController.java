package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.core.bookmark.BookmarkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@RestController
@RequestMapping("/api/v1")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    /**
     * ?sort=published_at|clipped_at&page=X&size=X (pagination)
     * User can see marked articles (only published_global and published_link article)
     */
    @GetMapping("/users/{username}/clipped-articles")
    public void getClippedArticlesOfUser(@PathVariable String username,
                                         @RequestParam(required = false) Optional<String> keyword,
                                         @RequestParam(required = false) Optional<Integer> page,
                                         @RequestParam(required = false) Optional<Integer> size,
                                         @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/articles/{slug}/subscribers")
    public void getAllSubscribersOfArticle(@PathVariable String slug) {

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
