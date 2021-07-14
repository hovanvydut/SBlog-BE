package hovanvydut.apiblog.model.entity;

import hovanvydut.apiblog.common.constant.ArticleVoteEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
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
public class ArticleVote implements Serializable {

    @EmbeddedId
    private ArticleVoteId id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("article_id")
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "vote", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private ArticleVoteEnum vote;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_edited_at")
    private LocalDateTime lastEditedAt;
}



