package hovanvydut.apiblog.entity;

import hovanvydut.apiblog.entity.enums.ArticleVoteType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/10/21
 */

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "article_vote")
public class ArticleVote {

    @EmbeddedId
    private ArticleVoteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articleId")
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "vote", nullable = false, columnDefinition = "TINYINT")
    private ArticleVoteType vote = ArticleVoteType.UP;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}



