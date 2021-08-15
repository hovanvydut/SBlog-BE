package hovanvydut.apiblog.core.vote;

import hovanvydut.apiblog.entity.enums.ArticleVoteType;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

public interface VoteService {
    void voteArticle(String slug, String username, ArticleVoteType vote);
}
