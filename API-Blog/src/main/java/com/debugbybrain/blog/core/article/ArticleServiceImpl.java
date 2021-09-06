package com.debugbybrain.blog.core.article;

import com.debugbybrain.blog.core.article.dto.UpdateSeriesDTO;
import com.debugbybrain.blog.common.exception.ArticleNotFoundException;
import com.debugbybrain.blog.common.exception.SeriesNotFoundException;
import com.debugbybrain.blog.common.exception.base.MyError;
import com.debugbybrain.blog.common.exception.base.MyRuntimeException;
import com.debugbybrain.blog.common.util.SlugUtil;
import com.debugbybrain.blog.common.util.SlugUtilImpl;
import com.debugbybrain.blog.common.util.SortAndPaginationUtil;
import com.debugbybrain.blog.core.article.dto.*;
import com.debugbybrain.blog.core.user.UserService;
import com.debugbybrain.blog.core.user.dto.RoleDTO;
import com.debugbybrain.blog.entity.*;
import com.debugbybrain.blog.entity.enums.ArticleScopeEnum;
import com.debugbybrain.blog.entity.enums.ArticleStatusEnum;
import com.debugbybrain.blog.entity.enums.ArticleType;
import com.debugbybrain.blog.entity.enums.SeriesArticleStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Validated
@Service
public class ArticleServiceImpl implements ArticleService {

    private final UserService userService;
    private final ArticleRepository articleRepo;
    private final SeriesArticleRepository seriesRepo;
    private final ModelMapper modelMapper;
    private final SlugUtil slugUtil = new SlugUtilImpl();

    public ArticleServiceImpl(UserService userService, ArticleRepository articleRepo, SeriesArticleRepository seriesRepo,
                              ModelMapper modelMapper) {
        this.userService = userService;
        this.articleRepo = articleRepo;
        this.seriesRepo = seriesRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public Page<ArticleDTO> getAllArticlesOrSeries(int page, int size, String[] sort, String searchKeyword, ArticleType type) {
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, sort);

        if (type == null) type = ArticleType.POST;

        Page<Article> articlePage;
        if (searchKeyword == null || searchKeyword.isBlank()) {
            articlePage = this.articleRepo.getAll(type, pageable);
        } else {
            articlePage = this.articleRepo.search(type, searchKeyword, pageable);
        }

        List<Article> articles = articlePage.getContent();
        List<ArticleDTO> articleDTOs = this.modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());

        return new PageImpl<>(articleDTOs, pageable, articlePage.getTotalElements());
    }


    @Override
    public Page<ArticleDTO> getAllPublishedArticlesOrSeries(Optional<String> username, ArticleType type, int page, int size,
                                                            String[] sort, String searchKeyword) {
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, sort);

        Page<Article> articlePage;
        if (searchKeyword == null || searchKeyword.isBlank()) {
            if (username.isEmpty()) {
                articlePage = this.articleRepo.findByStatus(type, ArticleStatusEnum.PUBLISHED_GLOBAL, pageable);
            } else {
                articlePage = this.articleRepo.findByStatusAndAuthor(type, username.get(),
                        ArticleStatusEnum.PUBLISHED_GLOBAL, pageable);
            }
        } else {
            if (username.isEmpty()) {
                articlePage = this.articleRepo.searchByStatus(type, searchKeyword, pageable,
                        ArticleStatusEnum.PUBLISHED_GLOBAL);
            } else {
                articlePage = this.articleRepo.searchByStatusAndAuthor(type, username.get(), searchKeyword,
                        ArticleStatusEnum.PUBLISHED_GLOBAL, pageable);
            }
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
    public SeriesDTO getSeries(String slug, String usernameViewer) {
        Article article = this.articleRepo.getSeriesBySlug(slug)
                .orElseThrow(() -> new SeriesNotFoundException(slug));

        ArticleStatusEnum articleStatus = article.getStatus();

        if (articleStatus == ArticleStatusEnum.PENDING || articleStatus== ArticleStatusEnum.DRAFT
                || articleStatus == ArticleStatusEnum.SPAM) {

            if (usernameViewer == null) throw new SeriesNotFoundException(slug);

            Long viewerId = this.userService.getUserIdByUsername(usernameViewer);

            if (!article.getAuthor().getId().equals(viewerId)) {
                throw new SeriesNotFoundException(slug);
            }

            return convertArticleToSeriesDTO(article);
        } else if (articleStatus == ArticleStatusEnum.PUBLISHED_GLOBAL
                || articleStatus == ArticleStatusEnum.PUBLISHED_LINK) {

            return convertArticleToSeriesDTO(article);
        }

        throw new SeriesNotFoundException(slug);
    }

    @Override
    @Transactional
    public ArticleDTO createNewArticle(@Valid CreateArticleDTO dto, PublishOption publishOption, String authorUsername,
                                       boolean isSeries) {
        if (publishOption == null) {
            throw new MyRuntimeException(List.of(new MyError().setMessage("Publish Option must be not null")));
        }

        // get author and validate username
        Long authorId = this.userService.getUserIdByUsername(authorUsername);

        // check if these articleIds are the articles (not series) and published and your own
        if (isSeries && dto.getArticleIds() != null && dto.getArticleIds().size() > 0) {
            long count = this.articleRepo.countValidYourPublishedArticle(dto.getArticleIds(), authorUsername);
            if (count != dto.getArticleIds().size()) {
                throw new RuntimeException("check if these articleIds are the articles (not series) and published and your own");
            }
        }

        // FIXME: Check if exist tagId not found (count(tag.id))

        // mapping dto --> entity
        Article article = new Article();
        this.modelMapper.map(dto, article);

        String slug = UUID.randomUUID().toString();
        String transliterated = this.slugUtil.slugify(article.getTitle());

        article.setId(null);
        article.setSlug(slug);
        article.setTransliterated(transliterated);
        article.setCategory(new Category().setId(dto.getCategoryId()));
        if (isSeries) article.setType(ArticleType.SERIES);

        for (long tagId : dto.getTagIds()) {
            article.getTags().add(new Tag().setId(tagId));
        }

        article.setAuthor(new User().setId(authorId));

        // switch case with PublishOption to set scope and status article
        assignProperlyPublishOption(article, publishOption);

        Article savedArticle = this.articleRepo.save(article);

        if (isSeries) {
            List<SeriesArticle> seriesList = new ArrayList<>();
            for (long id : dto.getArticleIds()) {
                SeriesArticle series = new SeriesArticle()
                        .setId(new SeriesArticleId().setSeriesId(savedArticle.getId()).setArticleId(id))
                        .setStatus(SeriesArticleStatus.ACCEPTED)
                        .setSeries(new Article().setId(savedArticle.getId()))
                        .setArticle(new Article().setId(id));

                seriesList.add(series);
            }
            this.seriesRepo.saveAll(seriesList);
        }

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
    public ArticleDTO updateArticle(String slug, UpdateArticleDTO dto, PublishOption publishOption,
                                    String authorUsername, boolean isSeries) {

        Article article = this.articleRepo.findBySlugAndAuthorUsernameEager(slug, authorUsername)
                .orElseThrow(() -> new ArticleNotFoundException(slug));

        // Mapping dto -> entity
        long seriesId = article.getId();
        if (isSeries) {
            UpdateSeriesDTO seriesDTO = (UpdateSeriesDTO) dto;
            this.modelMapper.map(seriesDTO, article);

            if (seriesDTO != null && seriesDTO.getArticleIds().size() > 0) {
                List<SeriesArticle> listNewArticle = new ArrayList<>();
                for (long id : seriesDTO.getArticleIds()) {
                    if (id == seriesId) throw new RuntimeException("Duplicate articleId and seriesId");

                    listNewArticle.add(new SeriesArticle().setId(new SeriesArticleId().setSeriesId(seriesId).setArticleId(id))
                            .setSeries(new Article().setId(seriesId)).setArticle(new Article().setId(id)));
                }
                article.setArticles(listNewArticle);
            }
        } else {
            this.modelMapper.map(dto, article);
        }

        article.setId(seriesId);
        article.setTransliterated(this.slugUtil.slugify(article.getTitle()));
        article.setCategory(new Category().setId(dto.getCategoryId()));

        if (dto.getTagIds() != null) {
            Set<Tag> newTags = new HashSet<>();

            for (long tagId : dto.getTagIds()) {
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
        Set<RoleDTO> roles = this.userService.getRolesByUsername(authorUsername);
        Long articleId;

        if (isContainAdminRole(roles)) {
            articleId = this.articleRepo.getIdBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
        } else {
            articleId = this.articleRepo.getIdBySlugAndAuthorUsername(slug, authorUsername)
                    .orElseThrow(() -> new ArticleNotFoundException(slug));
        }

        this.articleRepo.delete(new Article().setId(articleId));
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

    private boolean isContainAdminRole(Set<RoleDTO> roles) {
        if (roles == null || roles.size() == 0) return false;

        for (RoleDTO role : roles) {
            if ("ROLE_ADMIN".equals(role.getName())) {
                return true;
            }
        }

        return false;
    }

    // FIXME: Optmize by using Model Mapper custom
    private SeriesDTO convertArticleToSeriesDTO(Article article) {
        SeriesDTO seriesDTO = this.modelMapper.map(article, SeriesDTO.class);
        Set<Article> articlesSet = new HashSet<>();
        if (article.getArticles() != null && article.getArticles().size() > 0) {
            for (SeriesArticle item : article.getArticles()) {
                articlesSet.add(item.getArticle());
            }
        }
        seriesDTO.setArticles(this.modelMapper.map(articlesSet, new TypeToken<Set<SeriesDTO.ArticleDTO>>() {}.getType()));
        return seriesDTO;
    }
}
