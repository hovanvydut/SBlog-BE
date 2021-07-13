package hovanvydut.apiblog.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString
@Embeddable
public class ArticleVoteId implements Serializable {

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "article_id")
    private Long article_id;
}
