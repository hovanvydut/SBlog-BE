package hovanvydut.apiblog.core.article;

import hovanvydut.apiblog.common.util.SlugUtilImpl;
import hovanvydut.apiblog.entity.enums.ArticleScopeEnum;
import hovanvydut.apiblog.entity.enums.ArticleStatusEnum;
import hovanvydut.apiblog.common.exception.ArticleNotFoundException;
import hovanvydut.apiblog.common.exception.base.MyError;
import hovanvydut.apiblog.common.exception.base.MyRuntimeException;
import hovanvydut.apiblog.common.util.SlugUtil;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.article.dto.ArticleDTO;
import hovanvydut.apiblog.core.article.dto.CreateArticleDTO;
import hovanvydut.apiblog.core.article.dto.PublishOption;
import hovanvydut.apiblog.core.article.dto.UpdateArticleDTO;
import hovanvydut.apiblog.core.user.UserService;
import hovanvydut.apiblog.entity.Article;
import hovanvydut.apiblog.entity.Category;
import hovanvydut.apiblog.entity.Tag;
import hovanvydut.apiblog.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
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

    private final UserService userService;
    private final ArticleRepository articleRepo;
    private final ModelMapper modelMapper;
    private final SlugUtil slugUtil = new SlugUtilImpl();

    public ArticleServiceImpl(UserService userService, ArticleRepository articleRepo, ModelMapper modelMapper) {
        this.userService = userService;
        this.articleRepo = articleRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public Page<ArticleDTO> getAllArticles(int page, int size, String[] sort, String searchKeyword) {
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, sort);

        Page<Article> articlePage;
        if (searchKeyword == null || searchKeyword.isBlank()) {
            articlePage = this.articleRepo.findAll(pageable);
        } else {
            articlePage = this.articleRepo.search(searchKeyword, pageable);
        }

        List<Article> articles = articlePage.getContent();
        List<ArticleDTO> articleDTOs = this.modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());

        return new PageImpl<>(articleDTOs, pageable, articlePage.getTotalElements());
    }

    @Override
    public Page<ArticleDTO> getAllPublishedArticles(int page, int size, String[] sort, String searchKeyword) {
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, sort);

        Page<Article> articlePage;
        if (searchKeyword == null || searchKeyword.isBlank()) {
            articlePage = this.articleRepo.findByStatus(ArticleStatusEnum.PUBLISHED_GLOBAL, pageable);
        } else {
            articlePage = this.articleRepo.searchByStatus(searchKeyword, pageable, ArticleStatusEnum.PUBLISHED_GLOBAL);
        }

        List<Article> articles = articlePage.getContent();
        List<ArticleDTO> articleDTOs = this.modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());

        return new PageImpl<>(articleDTOs, pageable, articlePage.getTotalElements());
    }

    @Override
    public Page<ArticleDTO> getAllPublishedArticles(String username, int page, int size, String[] sort, String searchKeyword) {
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, sort);

        Page<Article> articlePage;
        if (searchKeyword == null || searchKeyword.isBlank()) {
            articlePage = this.articleRepo.findByStatusAndAuthor(username, ArticleStatusEnum.PUBLISHED_GLOBAL, pageable);
        } else {
            articlePage = this.articleRepo.searchByStatusAndAuthor(username, searchKeyword, ArticleStatusEnum.PUBLISHED_GLOBAL, pageable);
        }

        List<Article> articles = articlePage.getContent();
        List<ArticleDTO> articleDTOs = this.modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());

        return new PageImpl<>(articleDTOs, pageable, articlePage.getTotalElements());
    }

    @Override
    public ArticleDTO getArticle(String slug, String usernameViewer) {

        Article article = this.articleRepo.findBySlug(slug)
                .orElseThrow(() -> new ArticleNotFoundException(slug));

        ArticleStatusEnum articleStatus = article.getStatus();

        if (articleStatus == ArticleStatusEnum.PENDING || articleStatus== ArticleStatusEnum.DRAFT
                || articleStatus == ArticleStatusEnum.SPAM) {

            if (usernameViewer == null) throw new ArticleNotFoundException(slug);

            Long viewerId = this.userService.getUserIdByUsername(usernameViewer);

            if (!article.getAuthor().getId().equals(viewerId)) {
                throw new ArticleNotFoundException(slug);
            }

            return this.modelMapper.map(article, ArticleDTO.class);
        } else if (articleStatus == ArticleStatusEnum.PUBLISHED_GLOBAL
                || articleStatus == ArticleStatusEnum.PUBLISHED_LINK) {
            return this.modelMapper.map(article, ArticleDTO.class);
        }

        throw new ArticleNotFoundException(slug);
    }

    @Override
    @Transactional
    public ArticleDTO createNewArticle(CreateArticleDTO dto, PublishOption publishOption, String authorUsername) {
        if (publishOption == null) {
            throw new MyRuntimeException(List.of(new MyError().setMessage("Publish Option must be not null")));
        }

        // get author and validate username
        Long authorId = this.userService.getUserIdByUsername(authorUsername);

        // mapping dto --> entity
        Article article = new Article();
        this.modelMapper.map(dto, article);

        String slug = UUID.randomUUID().toString();
        String transliterated = this.slugUtil.slugify(article.getTitle());

        article.setId(null);
        article.setSlug(slug);
        article.setTransliterated(transliterated);
        article.setCategory(new Category().setId(dto.getCategoryId()));

        for (long tagId : dto.getTagIds()) {
            article.getTags().add(new Tag().setId(tagId));
        }

        article.setAuthor(new User().setId(authorId));

        // switch case with PublishOption to set scope and status article
        assignProperlyPublishOption(article, publishOption);

        Article savedArticle = this.articleRepo.save(article);

        return this.modelMapper.map(savedArticle, ArticleDTO.class);
    }

    @Override
    @Transactional
    public void approveArticle(String slug) {
        Article article = this.articleRepo.findBySlug(slug)
                .orElseThrow(() -> new ArticleNotFoundException(slug));

        switch (article.getScope()) {
            case GLOBAL:
                article.setStatus(ArticleStatusEnum.PUBLISHED_GLOBAL);
                break;
            case ONLY_WHO_HAS_LINK:
                article.setStatus(ArticleStatusEnum.PUBLISHED_LINK);
                break;
            default:
                // TODO: another scope is invalid
                throw new RuntimeException("Article Scope not found");
        }

        article.setPublishedAt(LocalDateTime.now());
        this.articleRepo.save(article);
    }

    @Override
    @Transactional
    public void markArticleSpam(String slug) {
        Article article = this.articleRepo.findBySlug(slug)
                .orElseThrow(() -> new ArticleNotFoundException(slug));

        article.setPublishedAt(null);
        article.setStatus(ArticleStatusEnum.SPAM);

        this.articleRepo.save(article);
    }

    @Override
    @Transactional
    public ArticleDTO updateArticle(String slug, UpdateArticleDTO dto, PublishOption publishOption, String authorUsername) {
        Article article = this.articleRepo.findBySlugAndAuthorUsername(slug, authorUsername)
                .orElseThrow(() -> new ArticleNotFoundException(slug));

        // Mapping dto -> entity
        this.modelMapper.map(dto, article);
        article.setTransliterated(this.slugUtil.slugify(article.getTitle()));
        article.setCategory(new Category().setId(dto.getCategory()));

        if (dto.getTags() != null) {
            Set<Tag> newTags = new HashSet<>();

            for (long tagId : dto.getTags()) {
                newTags.add(new Tag().setId(tagId));
            }

            article.setTags(newTags);
        }

        // switch case with PublishOption to set scope and status article
        assignProperlyPublishOption(article, publishOption);

        Article savedArticle = this.articleRepo.save(article);
        return this.modelMapper.map(savedArticle, ArticleDTO.class);
    }

    @Override
    @Transactional
    public void deleteArticle(String slug, String authorUsername) {
        Article article = this.articleRepo.findBySlugAndAuthorUsername(slug, authorUsername)
                .orElseThrow(() -> new ArticleNotFoundException(slug));

        this.articleRepo.delete(article);
    }

    @Override
    public Long getArticleIdBySlug(String slug) {
        return this.articleRepo.getArticleIdBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
    }

    private void assignProperlyPublishOption(Article article, PublishOption publishOption) {
        // NOTE: Violate Open/Close principle
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
                // TODO: add custom exception
                throw new RuntimeException("PublishOption does not match");
        }
    }

}
