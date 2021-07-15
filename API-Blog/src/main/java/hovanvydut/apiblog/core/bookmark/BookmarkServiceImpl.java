package hovanvydut.apiblog.core.bookmark;

import hovanvydut.apiblog.common.exception.ArticleNotFoundException;
import hovanvydut.apiblog.common.exception.MyRuntimeException;
import hovanvydut.apiblog.common.exception.MyUsernameNotFoundException;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.article.ArticleRepository;
import hovanvydut.apiblog.core.article.dto.ArticleDTO;
import hovanvydut.apiblog.core.bookmark.dto.SubscriberDTO;
import hovanvydut.apiblog.core.user.UserRepository;
import hovanvydut.apiblog.model.entity.Article;
import hovanvydut.apiblog.model.entity.BookmarkArticle;
import hovanvydut.apiblog.model.entity.BookmarkArticleId;
import hovanvydut.apiblog.model.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
    private final ModelMapper modelMapper;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepo,
                               ArticleRepository articleRepository,
                               UserRepository userRepository,
                               ModelMapper modelMapper) {
        this.bookmarkRepo = bookmarkRepo;
        this.articleRepo = articleRepository;
        this.userRepo = userRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public List<SubscriberDTO> getAllSubscribers(String slug) {
        Article article = this.articleRepo.findBySlug(slug);

        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }

        return this.bookmarkRepo.findAllSubscribersOfArticle(article.getId());
    }

    @Override
    public Page<ArticleDTO> getAllClippedArticlesOfUser(String username, int page, int size, String[] sort, String keyword) {
        User user = this.userRepo.findByUsername(username);

        if (user == null) {
            throw new MyUsernameNotFoundException(username);
        }

        Sort sortObj = SortAndPaginationUtil.processSort(sort);

        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        Page<Article> pageArticles;

        if (keyword == null || keyword.isBlank()) {
            pageArticles = this.bookmarkRepo.findAllClippedArticlesOfUser(user.getId(), pageable);
        } else {
            pageArticles = this.bookmarkRepo.searchByArticleTitle(keyword, user.getId(), pageable);
        }

        List<Article> articles = pageArticles.getContent();
        List<ArticleDTO> articleDTOs = this.modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());

        return new PageImpl<>(articleDTOs, pageable, pageArticles.getTotalElements());
    }
}
