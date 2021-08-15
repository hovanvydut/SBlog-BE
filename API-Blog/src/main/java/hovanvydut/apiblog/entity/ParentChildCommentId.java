package hovanvydut.apiblog.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author hovanvydut
 * Created on 8/7/21
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString
@Embeddable
public class ParentChildCommentId implements Serializable {

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "child_comment_id")
    private Long childCommentId;

}
