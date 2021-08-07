package hovanvydut.apiblog.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hovanvydut
 * Created on 8/7/21
 */

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "parent_child_comment")
public class ParentChildComment {

    @EmbeddedId
    private ParentChildCommentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("parentCommentId")
    @JoinColumn(nullable = false)
    private Comment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("childCommentId")
    @JoinColumn(nullable = false)
    private Comment childComment;

    @Column(name = "level", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private int level;

}


