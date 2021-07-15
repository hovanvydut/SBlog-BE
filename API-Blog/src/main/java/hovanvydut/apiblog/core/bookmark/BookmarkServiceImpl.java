package hovanvydut.apiblog.core.bookmark;

import hovanvydut.apiblog.common.exception.ArticleNotFoundException;
import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.common.exception.MyRuntimeException;
import hovanvydut.apiblog.common.exception.MyUsernameNotFoundException;
import hovanvydut.apiblog.core.article.ArticleRepository;
import hovanvydut.apiblog.core.user.UserRepository;
import hovanvydut.apiblog.model.entity.Article;
import hovanvydut.apiblog.model.entity.BookmarkArticle;
import hovanvydut.apiblog.model.entity.BookmarkArticleId;
import hovanvydut.apiblog.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@Validated
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepo;
    private final ArticleRepository articleRepo;
    private final UserRepository userRepo;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepo,
                               ArticleRepository articleRepository,
                               UserRepository userRepository) {
        this.bookmarkRepo = bookmarkRepo;
        this.articleRepo = articleRepository;
        this.userRepo = userRepository;
    }


    @Override
    public void clipArticle(String articleSlug, String subscriberUsername) {
        Article article = this.articleRepo.findBySlug(articleSlug);

        if (article == null) {
            throw new ArticleNotFoundException(articleSlug);
        }

        User subscriber = this.userRepo.findByUsername(subscriberUsername);

        if (subscriber == null) {
            throw new MyUsernameNotFoundException(subscriberUsername);
        }

        BookmarkArticleId bookmarkArticleId = new BookmarkArticleId().setArticleId(article.getId()).setUserId(subscriber.getId());

        this.bookmarkRepo.findById(bookmarkArticleId)
                .ifPresent(bookmarkArticle -> {
                    throw new MyRuntimeException().add("You have clipped this article before");
                });

        if (article.getAuthor().getId() == subscriber.getId()) {
            throw new MyRuntimeException().add("Author can't clipped owning articles");
        }


        BookmarkArticle bookmarkArticle = new BookmarkArticle().setId(bookmarkArticleId).setArticle(article).setUser(subscriber);

        this.bookmarkRepo.save(bookmarkArticle);
    }

    @Override
    public void deleteBookmark(String articleSlug, String subscriberUsername) {
        Article article = this.articleRepo.findBySlug(articleSlug);

        if (article == null) {
            throw new ArticleNotFoundException(articleSlug);
        }

        User subscriber = this.userRepo.findByUsername(subscriberUsername);

        if (subscriber == null) {
            throw new MyUsernameNotFoundException(subscriberUsername);
        }

        BookmarkArticleId bookmarkArticleId = new BookmarkArticleId()
                .setArticleId(article.getId()).setUserId(subscriber.getId());
        BookmarkArticle bookmarkArticle = this.bookmarkRepo.findById(bookmarkArticleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleSlug));

        this.bookmarkRepo.delete(bookmarkArticle);
    }
}
