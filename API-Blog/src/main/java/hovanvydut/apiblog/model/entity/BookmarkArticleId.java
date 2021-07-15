package hovanvydut.apiblog.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author hovanvydut
 * Created on 7/14/21
 */

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString
@Embeddable
public class BookmarkArticleId  implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "article_id")
    private Long articleId;

}
