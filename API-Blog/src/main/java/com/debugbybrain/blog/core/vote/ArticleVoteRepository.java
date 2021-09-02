package com.debugbybrain.blog.core.vote;

import com.debugbybrain.blog.entity.ArticleVote;
import com.debugbybrain.blog.entity.ArticleVoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

@Repository
public interface ArticleVoteRepository extends JpaRepository<ArticleVote, ArticleVoteId> {

}
