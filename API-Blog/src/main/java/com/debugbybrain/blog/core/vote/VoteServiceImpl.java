package com.debugbybrain.blog.core.vote;

import com.debugbybrain.blog.core.article.ArticleService;
import com.debugbybrain.blog.core.user.UserService;
import com.debugbybrain.blog.entity.Article;
import com.debugbybrain.blog.entity.ArticleVote;
import com.debugbybrain.blog.entity.ArticleVoteId;
import com.debugbybrain.blog.entity.User;
import hovanvydut.apiblog.entity.enums.ArticleVoteType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

@Service
public class VoteServiceImpl implements VoteService{

    private final ArticleVoteRepository articleVoteRepo;
    private final ArticleService articleService;
    private final UserService userService;

    public VoteServiceImpl(ArticleVoteRepository articleVoteRepo, ArticleService articleService, UserService userService) {
        this.articleVoteRepo = articleVoteRepo;
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void voteArticle(String slug, String username, ArticleVoteType vote) {
        Long articleId = this.articleService.getArticleIdBySlug(slug);
        Long userId = this.userService.getUserIdByUsername(username);

        ArticleVote articleVote = new ArticleVote().setId(new ArticleVoteId(userId, articleId))
                .setVote(vote).setArticle(new Article().setId(articleId)).setUser(new User().setId(userId));

        this.articleVoteRepo.save(articleVote);
    }
}
