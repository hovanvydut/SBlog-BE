package hovanvydut.apiblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author hovanvydut
 * Created on 8/15/21
 */

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "series_article")
public class SeriesArticle {

    @EmbeddedId
    private SeriesArticleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("seriesId")
    @JoinColumn(name = "series_id", nullable = false)
    private Article series;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articleId")
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

}
