package hovanvydut.apiblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author hovanvydut
 * Created on 8/15/21
 */

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Embeddable
public class SeriesArticleId implements Serializable {

    @Column(name = "series_id")
    private Long seriesId;

    @Column(name = "article_id")
    private Long articleId;

}
