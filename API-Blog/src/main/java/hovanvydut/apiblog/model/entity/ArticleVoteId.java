package hovanvydut.apiblog.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Embeddable
public class ArticleVoteId implements Serializable {

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "article_id")
    private Long article_id;
}
