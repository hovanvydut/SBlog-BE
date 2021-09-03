package com.debugbybrain.blog.core.vote;

import com.debugbybrain.blog.entity.enums.ArticleVoteType;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

public interface VoteService {
    void voteArticle(String slug, String username, ArticleVoteType vote);
}
