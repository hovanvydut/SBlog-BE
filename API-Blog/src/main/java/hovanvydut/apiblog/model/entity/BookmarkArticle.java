package hovanvydut.apiblog.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/14/21
 */

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "bookmark_article")
public class BookmarkArticle {

    @EmbeddedId
    private BookmarkArticleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
