package hovanvydut.apiblog.core.vote;

import hovanvydut.apiblog.entity.ArticleVote;
import hovanvydut.apiblog.entity.ArticleVoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

@Repository
public interface ArticleVoteRepository extends JpaRepository<ArticleVote, ArticleVoteId> {

}
