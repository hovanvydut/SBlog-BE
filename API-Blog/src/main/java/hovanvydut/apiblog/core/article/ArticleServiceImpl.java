package hovanvydut.apiblog.core.article;

import hovanvydut.apiblog.common.constant.ArticleScopeEnum;
import hovanvydut.apiblog.common.constant.ArticleStatusEnum;
import hovanvydut.apiblog.common.exception.ArticleNotFoundException;
import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.common.exception.MyRuntimeException;
import hovanvydut.apiblog.common.exception.MyUsernameNotFoundException;
import hovanvydut.apiblog.common.util.SlugUtil;
import hovanvydut.apiblog.core.article.dto.ArticleDTO;
import hovanvydut.apiblog.core.article.dto.CreateArticleDTO;
import hovanvydut.apiblog.core.article.dto.PublishOption;
import hovanvydut.apiblog.core.article.dto.UpdateArticleDTO;
import hovanvydut.apiblog.core.user.UserRepository;
import hovanvydut.apiblog.model.entity.Article;
import hovanvydut.apiblog.model.entity.Category;
import hovanvydut.apiblog.model.entity.Tag;
import hovanvydut.apiblog.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Validated
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepo;
    private final ModelMapper modelMapper;
    private final UserRepository userRepo;

    public ArticleServiceImpl(ArticleRepository articleRepo,
                              ModelMapper modelMapper,
                              UserRepository userRepository) {
        this.articleRepo = articleRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepository;
    }

    @Override
    public ArticleDTO getArticle(String slug, String usernameViewer) {

        Article article = this.articleRepo.findBySlug(slug);

        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }

        if (article.getStatus() != ArticleStatusEnum.PUBLISHED_GLOBAL
                && article.getStatus() != ArticleStatusEnum.PUBLISHED_LINK) {

            if (usernameViewer == null) {
                throw new ArticleNotFoundException(slug);
            }

            User user = this.userRepo.findByUsername(usernameViewer);

            if (user == null) {
                throw new UsernameNotFoundException(usernameViewer);
            }

            if (article.getAuthor().getId() != user.getId()) {
                throw new ArticleNotFoundException(slug);
            }

        }

        return this.modelMapper.map(article, ArticleDTO.class);
    }

    @Override
    public ArticleDTO createNewArticle(CreateArticleDTO dto, PublishOption publishOption, String authorUsername) {
        if (publishOption == null) {
            throw new MyRuntimeException(List.of(new MyError().setMessage("Publish Option must be not null")));
        }

        // get author and validate username
        User author = this.userRepo.findByUsername(authorUsername);

        if (author == null) {
            throw new MyUsernameNotFoundException(authorUsername);
        }

        // mapping dto --> entity
        Article article = new Article();
        this.modelMapper.map(dto, article);

        String slug = UUID.randomUUID().toString();
        String transliterated = SlugUtil.slugify(article.getTitle());

        article.setId(null);
        article.setSlug(slug);
        article.setTransliterated(transliterated);
        article.setCategory(new Category().setId(dto.getCategoryId()));

        for (long tagId : dto.getTagIds()) {
            article.getTags().add(new Tag().setId(tagId));
        }

        article.setAuthor(author);

        // switch case with PublishOption to set scope and status article
        switch (publishOption) {
            case GLOBAL_PUBLISH:
                article.setScope(ArticleScopeEnum.GLOBAL);
                article.setStatus(ArticleStatusEnum.PENDING);
                break;
            case PRIVATE_LINK_PUBLISH:
                article.setScope(ArticleScopeEnum.ONLY_WHO_HAS_LINK);
                article.setStatus(ArticleStatusEnum.PENDING);
                break;
            case DRAFT:
                article.setScope(ArticleScopeEnum.GLOBAL);
                article.setStatus(ArticleStatusEnum.DRAFT);
                break;
            default:
                throw new RuntimeException("PublishOption does not match");
        }

        Article savedArticle = this.articleRepo.save(article);

        return this.modelMapper.map(savedArticle, ArticleDTO.class);
    }

    @Override
    public void approveArticle(String slug) {
        Article article = this.articleRepo.findBySlug(slug);

        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }

        switch (article.getScope()) {
            case GLOBAL:
                article.setStatus(ArticleStatusEnum.PUBLISHED_GLOBAL);
                break;
            case ONLY_WHO_HAS_LINK:
                article.setStatus(ArticleStatusEnum.PUBLISHED_LINK);
                break;
            default:
                throw new RuntimeException("Article Scope not found");
        }

        article.setPublishedAt(LocalDateTime.now());
        this.articleRepo.save(article);
    }

    @Override
    public void markArticleSpam(String slug) {
        Article article = this.articleRepo.findBySlug(slug);

        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }

        article.setPublishedAt(null);
        article.setStatus(ArticleStatusEnum.SPAM);

        this.articleRepo.save(article);
    }

    @Override
    public ArticleDTO updateArticle(String slug, UpdateArticleDTO dto, PublishOption publishOption, String authorUsername) {
        Article article = this.articleRepo.findBySlug(slug);

        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }

        if (!article.getAuthor().getUsername().equals(authorUsername)) {
            throw new RuntimeException("User with username = '" + authorUsername + "' is not owning this article");
        }

        this.modelMapper.map(dto, article);

        Set<Tag> newTags = new HashSet<>();
        for (long tagId : dto.getTagIds()) {
            newTags.add(new Tag().setId(tagId));
        }

        article.setLastUpdatedAt(LocalDateTime.now());

        // switch case with PublishOption to set scope and status article
        switch (publishOption) {
            case GLOBAL_PUBLISH:
                article.setScope(ArticleScopeEnum.GLOBAL);
                article.setStatus(ArticleStatusEnum.PENDING);
                break;
            case PRIVATE_LINK_PUBLISH:
                article.setScope(ArticleScopeEnum.ONLY_WHO_HAS_LINK);
                article.setStatus(ArticleStatusEnum.PENDING);
                break;
            case DRAFT:
                article.setScope(ArticleScopeEnum.GLOBAL);
                article.setStatus(ArticleStatusEnum.DRAFT);
                break;
            default:
                throw new RuntimeException("PublishOption does not match");
        }

        article.setTags(newTags);

        Article savedArticle = this.articleRepo.save(article);

        return this.modelMapper.map(savedArticle, ArticleDTO.class);
    }

}
